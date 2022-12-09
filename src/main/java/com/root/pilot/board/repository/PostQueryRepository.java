package com.root.pilot.board.repository;

import static com.root.pilot.board.domain.QPost.post;
import static com.root.pilot.user.domain.QUser.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PostListResponseDto;
import com.root.pilot.board.dto.QPostListResponseDto;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class  PostQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public PostQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Post> getPostsList(Pageable pageable, String keyword) {

        BooleanBuilder builder = getBooleanBuilder(keyword);

        List<Post> postsList = query
            .selectFrom(post)
            .where(builder)
            .orderBy(post.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(post.count())
            .where(builder)
            .from(post);

        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }

    public Page<PostListResponseDto> getPostsDtoList(Pageable pageable, String keyword) {

//        BooleanBuilder builder = getBooleanBuilder(keyword);
        BooleanBuilder builder = getLikeBuilder(keyword);

        List<Long> ids = toPostIds(pageable.getOffset(), pageable.getPageSize(), builder);

        List<PostListResponseDto> postsList = query
            .select(new QPostListResponseDto(
                    post.id,
                    post.title,
                    user.name,
                    post.createdDate)
            )
            .from(post)
            .leftJoin(post.user, user)
            .where(post.id.in(ids))
            .orderBy(post.id.desc())
            .fetch();


        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(post.count())
            .from(post)
            .where(builder);


        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }
    public Page<Post> getPostsCoveringIndexList(Pageable pageable, String keyword) {

        BooleanBuilder builder = getBooleanBuilder(keyword);

        List<Long> ids = toPostIds(pageable.getOffset(), pageable.getPageSize(), builder);

        List<Post> postsList = query
            .selectFrom(post)
            .where(post.id.in(ids))
            .orderBy(post.id.desc())
            .fetch();

        //count 구하기
        JPAQuery<Long> countQuery = query
            .select(post.count())
            .from(post)
            .where(builder);


        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);

    }

    //no offset
    public Slice<PostListResponseDto> paginationNoOffset(Long postId, Long userId, Pageable pageable, String keyword) {

        BooleanBuilder builder = new BooleanBuilder();

        if(postId != null) {
            builder.and(post.id.lt(postId));
        }

        builder.and(post.user.id.eq(userId)).and(getLikeBuilder(keyword));

        List<PostListResponseDto> posts = query
                .select(new QPostListResponseDto(
                    post.id,
                    post.title,
                    user.name,
                    post.createdDate)
                )
                .from(post)
                .leftJoin(post.user, user)
                .where(builder)
                .limit(pageable.getPageSize()+1)
                .orderBy(post.id.desc())
                .fetch();

        return getSliceImpl(pageable, posts);
    }

    private static SliceImpl<PostListResponseDto> getSliceImpl(Pageable pageable,
        List<PostListResponseDto> posts) {

        if(posts.size() == (pageable.getPageSize() + 1)) {
            return new SliceImpl<>(posts.subList(0, pageable.getPageSize()), pageable, true);
        }

        return new SliceImpl<>(posts, pageable, false);
    }

    private static BooleanBuilder getBooleanBuilder(String keyword) {

        NumberTemplate booleanTemplate = Expressions.numberTemplate(Double.class,
            "function('match',{0},{1})", post.title, keyword+"*");

        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(booleanTemplate.gt(0));
        }
        return builder;
    }

    private static BooleanBuilder getLikeBuilder(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(keyword)) {
            builder.and(post.title.like("%" + keyword + "%"));
        }
        return builder;
    }

    public List<Long> toPostIds(Long offset, int size, BooleanBuilder builder) {
        List<Long> ids = query
            .select(post.id)
            .from(post)
            .where(builder)
            .orderBy(post.id.desc())
            .offset(offset)
            .limit(size)
            .fetch();

        return ids;
    }

    public Page<Post> findPostsByUser(Pageable pageable, Long userId) {

        List<Post> postsList = query
            .selectFrom(post)
            .where(post.user.id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(post.id.desc())
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(post.count())
            .where(post.user.id.eq(userId))
            .from(post);

        return PageableExecutionUtils.getPage(postsList, pageable, countQuery::fetchOne);


    }

}
