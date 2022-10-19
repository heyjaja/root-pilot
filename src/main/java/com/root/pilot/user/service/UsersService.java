package com.root.pilot.user.service;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.security.jwt.TokenService;
import com.root.pilot.security.service.CustomUserDetailsService;
import com.root.pilot.user.domain.Users;
import com.root.pilot.user.dto.LoginRequestDto;
import com.root.pilot.user.dto.SignUpRequestDto;
import com.root.pilot.user.repository.UsersRepository;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public Long registerUser(SignUpRequestDto requestDto) {
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new EntityExistsException("해당 이메일이 존재합니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        return usersRepository.save(requestDto.toEntity(encodedPassword)).getId();

    }

    @Transactional
    public String signInUser(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Users user = usersRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        if(passwordEncoder.matches(password, user.getPassword())) {

            String accessToken = tokenService.createAccessToken(user);

            return accessToken;
        }

        throw new RuntimeException("비밀번호가 틀림");



    }

}
