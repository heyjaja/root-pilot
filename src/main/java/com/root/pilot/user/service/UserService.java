package com.root.pilot.user.service;

import com.root.pilot.user.domain.User;
import com.root.pilot.user.dto.UserListResponseDto;
import com.root.pilot.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserListResponseDto findAll() {
        List<User> users = userRepository.findAll();

        return new UserListResponseDto(users);
    }
}
