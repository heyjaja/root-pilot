package com.root.pilot.commons.util.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.security.jwt.TokenProvider;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TokenProviderTest {

    @Autowired
    TokenProvider tokenProvider;

    @Test
    void createTokenTest() {
        //given
        Users user = Users.builder()
            .email("test@test.test")
            .name("test")
            .password("test1")
            .picture("picture")
            .role(Role.USER)
            .authProvider(AuthProvider.local)
            .build();

        Map<String, Object> claims = new HashMap<>();

        claims.put("sub", "access-token");
        claims.put("id", 1L);
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());
        claims.put("role", user.getRoleKey());

        // then
        String token = tokenProvider.createAccessToken(claims);

        //when
        assertThat(tokenProvider.getClaims(token).get("name")).isEqualTo("test");
    }

}