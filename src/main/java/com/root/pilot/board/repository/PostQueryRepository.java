package com.root.pilot.board.repository;

import static com.root.pilot.board.domain.QPost.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.root.pilot.board.domain.Post;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Post> getPostsList(Pageable pageable) {
        List<Post> postsList = query
            .selectFrom(post)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(post.id.desc())
            .fetch();

        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(post.count())
            .from(post);

        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }

}
