package com.root.pilot.security.dto;

import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@ToString
public class CustomUserDetails implements UserDetails, OAuth2User{

    private Long id;
    private String email;
    private String name;
    private Role role;
    private AuthProvider authProvider;

    @Builder
    public CustomUserDetails(Long id, String email, String name,
        Role role, AuthProvider authProvider) {
        super();
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.authProvider = authProvider;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authority = new ArrayList<>();
        authority.add(new SimpleGrantedAuthority(role.toString()));
        return authority;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
