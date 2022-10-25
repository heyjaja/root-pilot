package com.root.pilot.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.root.pilot.commons.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @JsonIgnore
    private String password;
    private String picture;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Boolean emailVerified = false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Builder
    public User(String email, String password, String name, Role role, String picture, AuthProvider authProvider) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.picture = picture;
        this.authProvider = authProvider;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }
    public String getAuthProviderForToString() {
        return authProvider.toString();
    }

    public String getRoleForToString() {
        return role.toString();
    }


}
