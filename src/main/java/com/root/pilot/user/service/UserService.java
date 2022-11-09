package com.root.pilot.user.service;

import com.root.pilot.board.repository.PostQueryRepository;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.dto.UpdateUserRequestDto;
import com.root.pilot.user.dto.UserListResponseDto;
import com.root.pilot.user.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public UserListResponseDto findAll() {
        List<User> users = userRepository.findAll();

        return new UserListResponseDto(users);
    }

    public Long updateUserName(UpdateUserRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(
            () -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        authService.existsByName(requestDto.getName());

        user.updateName(requestDto.getName());

        return user.getId();
    }
}
