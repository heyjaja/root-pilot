package com.root.pilot.board.dto.reply;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.board.dto.PostListResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class ReplyListResponseDto {
    private List<ReplyResponseDto> replies;
    private Long totalPages;
    private Long totalCount;

    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;

    private List<Integer> pageList;

    @Builder
    public ReplyListResponseDto(List<Reply> replies, Long totalPages, Long totalCount, Pageable pageable) {
        this.replies = replies.stream()
            .map(reply -> new ReplyResponseDto(reply)).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        makePageList(pageable);
    }

    public void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;

        prev = start > 1;
        end = (int) Math.min(totalPages, tempEnd);

        next = totalPages > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
