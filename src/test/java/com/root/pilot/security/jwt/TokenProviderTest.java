package com.root.pilot.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    @InjectMocks TokenService tokenService;
    @Mock TokenProvider tokenProvider;

    @Test
    void createTokenTest() {
        //given
        CustomUserDetails user = CustomUserDetails.builder()
            .email("test@test.test")
            .name("test")
            .role(Role.ROLE_USER)
            .authProvider(AuthProvider.local)
            .build();

        // then
        String token = tokenService.createAccessToken(user);

        //when
        assertThat(tokenService.getUserDetailsByToken(token).getName()).isEqualTo("test");
    }

}