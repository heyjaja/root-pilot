package com.root.pilot.board.service;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.board.dto.reply.ReplyListResponseDto;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
import com.root.pilot.board.dto.reply.ReplyUpdateRequestDto;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.board.repository.ReplyQueryRepository;
import com.root.pilot.board.repository.ReplyRepository;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyQueryRepository replyQueryRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 작성
     */
    public Long save(ReplySaveRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("사용자가 존재하지 않습니다."));
        Post post = postRepository.findById(requestDto.getPostId())
            .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        Reply reply = Reply.builder()
            .content(requestDto.getContent())
            .user(user)
            .post(post)
            .build();

        return replyRepository.save(reply).getId();
    }

    /**
     * 댓글 수정
     */
    public Long update(Long id, ReplyUpdateRequestDto requestDto) {
        Reply reply = replyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!reply.validateUser(requestDto.getLoginUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        };

        reply.update(requestDto.getContent());

        return id;
    }

    /**
     * 댓글 삭제
     */
    public Long delete(Long replyId, Long userId) {
         Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!reply.validateUser(userId)) {
            throw new IllegalArgumentException("본인만 삭제할 수 있습니다.");
        };

        replyRepository.delete(reply);

        return replyId;
    }

    /**
     * 댓글목록
     */
    @Transactional(readOnly = true)
    public ReplyListResponseDto findReplyByPostId(Long postId, Pageable pageable) {
        postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."));

        Page<Reply> result = replyQueryRepository.findReplyByPostId(postId, pageable);

        return ReplyListResponseDto.builder()
            .replies(result.getContent())
            .totalCount(result.getTotalElements())
            .totalPages((long)result.getTotalPages())
            .pageable(pageable)
            .build();
    }


}
