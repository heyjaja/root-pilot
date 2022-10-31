package com.root.pilot.board.service;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.board.dto.PostResponseDto;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
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
public class ReplyService {

    private final ReplyRepository replyRepository;
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

}
