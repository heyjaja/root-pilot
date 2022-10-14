package com.root.pilot.board.repository;

import static com.root.pilot.board.domain.QPosts.posts;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.root.pilot.board.domain.Posts;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class PostsQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostsQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Posts> getPostsList(Pageable pageable) {
        List<Posts> postsList = query
            .selectFrom(posts)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(posts.id.desc())
            .fetch();

        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(posts.count())
            .from(posts);

        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }

}
