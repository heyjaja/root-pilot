package com.root.pilot.user.dto;

import com.root.pilot.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class UserListResponseDto {
    private List<UserResponseDto> users;

    public UserListResponseDto(List<User> users) {
        this.users = users.stream()
            .map(user -> new UserResponseDto(user)).collect(Collectors.toList());
    }
}
