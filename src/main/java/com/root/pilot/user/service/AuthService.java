package com.root.pilot.user.service;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.security.jwt.TokenService;
import com.root.pilot.security.service.CustomUserDetailsService;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.dto.LoginRequestDto;
import com.root.pilot.user.dto.AuthResponseDto;
import com.root.pilot.user.dto.SignUpRequestDto;
import com.root.pilot.user.repository.UserRepository;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @Transactional
    public Long registerUser(SignUpRequestDto requestDto) {
        existsByName(requestDto.getName());
        existsByEmail(requestDto.getEmail());

        if(!requestDto.validatePassword()) {
            throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않습니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        return userRepository.save(requestDto.toEntity(encodedPassword)).getId();

    }

    @Transactional(readOnly = true)
    public void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EntityExistsException("해당 이메일이 존재합니다.");
        }
    }

    @Transactional(readOnly = true)
    public void existsByName(String name) {
        if (userRepository.existsByName(name)) {
            throw new EntityExistsException("해당 이름이 존재합니다.");
        }
    }

    @Transactional
    public AuthResponseDto signInUser(LoginRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        User user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        if(passwordEncoder.matches(password, user.getPassword())) {

            CustomUserDetails userDetails = customUserDetailsService.loadUserById(user.getId());

            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = tokenService.createAccessToken(userDetails);

            return new AuthResponseDto(accessToken);
        }

        throw new IllegalArgumentException("비밀번호를 확인해주세요.");



    }

}
