package com.root.pilot.security.service;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.security.jwt.TokenService;
import com.root.pilot.user.domain.Users;
import com.root.pilot.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    private final TokenService tokenService;


    public CustomUserDetails create(Users user) {

        return CustomUserDetails.builder()
            .id(user.getId())
            .email(user.getEmail())
            .authProvider(user.getAuthProvider())
            .role(user.getRole())
            .name(user.getName())
            .build();
    }

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+email));

        return create(user);
    }

    @Transactional
    public CustomUserDetails loadUserById(Long id) {
        Users user = usersRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not Found " + id));

        return create(user);
    }

    @Transactional
    public CustomUserDetails loadUserByToken(String token) {
        CustomUserDetails userDetails = tokenService.getUserDetailsByToken(token);

        return loadUserByUsername(userDetails.getEmail());

    }

}