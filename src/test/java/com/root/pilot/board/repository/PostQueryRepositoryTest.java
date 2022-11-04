package com.root.pilot.board.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostQueryRepositoryTest {

    @Autowired
    private PostQueryRepository postQueryRepository;

    @Test
    public void TestGetPostsMySql() {

    }
}