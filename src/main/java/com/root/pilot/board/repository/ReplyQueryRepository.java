package com.root.pilot.board.repository;

import static com.root.pilot.board.domain.QReply.reply;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.root.pilot.board.domain.Reply;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public ReplyQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Reply> findReplyByPostId(Long postId, Pageable pageable) {

        List<Reply> replies = query
            .selectFrom(reply)
            .where(reply.post.id.eq(postId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(reply.id.desc())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(reply.count())
            .where(reply.post.id.eq(postId))
            .from(reply);

        return PageableExecutionUtils.getPage(replies, pageable, countQuery::fetchOne);


    }

}
