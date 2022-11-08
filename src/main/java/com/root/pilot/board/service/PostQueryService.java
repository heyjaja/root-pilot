package com.root.pilot.board.service;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.PostListResponseDto;
import com.root.pilot.board.dto.PostListWithPageResponseDto;
import com.root.pilot.board.dto.UserPostsResponseDto;
import com.root.pilot.board.repository.PostQueryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostQueryRepository postQueryRepository;


    public PostListWithPageResponseDto getListWithPaging(Pageable pageable, String keyword) {

        Page<Post> results = postQueryRepository.getPostsList(pageable, keyword);
        List<PostListResponseDto> postsList = getPostListResponseDtoList(
            results);

        return PostListWithPageResponseDto.builder()
            .postsList(postsList)
            .totalCount(results.getTotalElements())
            .totalPages((long)results.getTotalPages())
            .keyword(keyword)
            .pageable(pageable)
            .build();
    }

    public PostListWithPageResponseDto getListCoveringIndexWithPaging(Pageable pageable, String keyword) {
        Page<Post> results = postQueryRepository.getPostsCoveringIndexList(pageable, keyword);

        return PostListWithPageResponseDto.builder()
            .postsList(getPostListResponseDtoList(results))
            .totalCount(results.getTotalElements())
            .totalPages((long)results.getTotalPages())
            .keyword(keyword)
            .pageable(pageable)
            .build();
    }

    public PostListWithPageResponseDto getDtoListWithPaging(Pageable pageable, String keyword) {
        Page<PostListResponseDto> results = postQueryRepository.getPostsDtoList(pageable, keyword);

        return PostListWithPageResponseDto.builder()
            .postsList(results.getContent())
            .totalCount(results.getTotalElements())
            .totalPages((long)results.getTotalPages())
            .keyword(keyword)
            .pageable(pageable)
            .build();
    }

    // 내가 쓴 글
    public PostListWithPageResponseDto findPostsByUser(Pageable pageable, Long userId) {

        Page<Post> result = postQueryRepository.findPostsByUser(pageable, userId);
        List<PostListResponseDto> postsList = getPostListResponseDtoList(
            result);

        return PostListWithPageResponseDto.builder()
            .postsList(postsList)
            .totalCount(result.getTotalElements())
            .totalPages((long)result.getTotalPages())
            .pageable(pageable)
            .build();
    }

    public UserPostsResponseDto getUserPosts(Long postId, Long userId, Pageable pageable, String keyword) {
        Slice<PostListResponseDto> result =
            postQueryRepository.paginationNoOffset(postId, userId, pageable, keyword);

        List<PostListResponseDto> postsList = result.getContent();

        if(postsList.size() == 0) {
            throw new IllegalArgumentException("검색 결과가 존재하지 않습니다.");
        }

        return UserPostsResponseDto.builder()
            .postsList(postsList)
            .next(result.hasNext())
            .lastPostId(postsList.get(postsList.size() - 1).getPostId())
            .build();
    }

    private static List<PostListResponseDto> getPostListResponseDtoList(Page<Post> result) {
        List<PostListResponseDto> postsList = result.getContent().stream()
            .map(post -> new PostListResponseDto(post)).collect(
                Collectors.toList());
        return postsList;
    }
}
