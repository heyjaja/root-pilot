package com.root.pilot.board.dto;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequestDto {

    private int page;
    private int size;
    private String keyword;

    public PageRequestDto() {
        this.page = 1;
        this.size = 20;
    }

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
