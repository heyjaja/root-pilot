package com.root.pilot.board.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.root.pilot.board.dto.QPostListResponseDto is a Querydsl Projection type for PostListResponseDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostListResponseDto extends ConstructorExpression<PostListResponseDto> {

    private static final long serialVersionUID = 1595027956L;

    public QPostListResponseDto(com.querydsl.core.types.Expression<Long> postId, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> user, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdDate) {
        super(PostListResponseDto.class, new Class<?>[]{long.class, String.class, String.class, java.time.LocalDateTime.class}, postId, title, user, createdDate);
    }

}

