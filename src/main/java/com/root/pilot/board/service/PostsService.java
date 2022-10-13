package com.root.pilot.board.service;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.domain.PostsRepository;
import com.root.pilot.board.dto.PostsListResponseDto;
import com.root.pilot.board.dto.PostsResponseDto;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import com.root.pilot.board.dto.PostsUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    // 글쓰기
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    //글수정
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto updateRequestDto) {
        Posts post = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        post.update(updateRequestDto.getTitle(), updateRequestDto.getContent());

        return id;
    }

    // 글삭제
    @Transactional
    public Long delete(Long id) {
        Posts post = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(post);

        return id;
    }

    // 글조회
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    // 글목록
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
            .map(PostsListResponseDto::new)
            .collect(Collectors.toList());
    }


}
