package com.root.pilot.board.repository;

import com.root.pilot.board.domain.Post;
import com.root.pilot.commons.Timer;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Timer
    @Query(value = "select * from post use index for order by (PRIMARY) order by post_id desc limit ?1, ?2", nativeQuery = true)
    List<Post> findAllTemp(Integer page, Integer size);

    @Timer
    @Query(value = "select * from post use index for order by (PRIMARY) order by post_id desc limit 10038780, 10", nativeQuery = true)
    List<Post> findAllTemp2();

    public Page<Post> findAllByOrderByIdDesc(Pageable pageable);
}
