package com.root.pilot.board.service;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.repository.PostsRepository;
import com.root.pilot.board.dto.PostsListWithPageResponseDto;
import com.root.pilot.board.dto.PostsResponseDto;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import com.root.pilot.board.dto.PostsUpdateRequestDto;
import com.root.pilot.board.repository.PostsQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final PostsQueryRepository postsQueryRepository;

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

    // 글목록 페이징
    @Transactional(readOnly = true)
    public PostsListWithPageResponseDto getListWithPaging(PageRequestDto pageRequestDto) {

        Pageable pageable = pageRequestDto.getPageable();

        Page<Posts> results = postsQueryRepository.getPostsList(pageable);

        return PostsListWithPageResponseDto.builder()
            .postsList(results.getContent())
            .totalCount(results.getTotalElements())
            .totalPages((long)results.getTotalPages())
            .pageable(pageable)
            .build();
    }

}
