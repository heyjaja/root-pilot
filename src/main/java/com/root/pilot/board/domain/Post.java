package com.root.pilot.board.domain;

import com.root.pilot.commons.BaseEntity;
import com.root.pilot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // user 확인
    public boolean validateUser(Long userId) {
        return this.user.getId().equals(userId);
    }
}
