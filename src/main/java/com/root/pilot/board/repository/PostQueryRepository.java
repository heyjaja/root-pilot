package com.root.pilot.board.repository;

import static com.root.pilot.board.domain.QPost.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.root.pilot.board.domain.Post;
import com.root.pilot.user.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Post> getPostsList(Pageable pageable, String keyword) {

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(post.title.like("%" + keyword + "%"));
        }

        List<Post> postsList = query
            .selectFrom(post)
            .where(builder)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(post.id.desc())
            .fetch();

        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(post.count())
            .where(builder)
            .from(post);

        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }

}
