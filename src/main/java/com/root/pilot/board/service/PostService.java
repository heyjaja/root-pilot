package com.root.pilot.board.service;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PostResponseDto;
import com.root.pilot.board.dto.PostSaveRequestDto;
import com.root.pilot.board.dto.PostUpdateRequestDto;
import com.root.pilot.board.repository.PostQueryRepository;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.board.repository.ReplyRepository;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    // 글쓰기
    public Long save(PostSaveRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));

        Post post = Post.builder().title(requestDto.getTitle())
            .content(requestDto.getContent())
            .user(user)
            .build();

        return postRepository.save(post).getId();
    }

    //글수정
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if(!post.validateUser(requestDto.getLoginUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        };

        post.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    // 글삭제
    public Long delete(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if(!post.validateUser(userId)) {
            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");
        };

        replyRepository.deleteAllByPostId(postId);
        postRepository.delete(post);

        return postId;
    }

    // 글조회
    @Transactional(readOnly = true)
    public PostResponseDto findById(Long id) {
        Post entity = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다"));

        return new PostResponseDto(entity);
    }

}
