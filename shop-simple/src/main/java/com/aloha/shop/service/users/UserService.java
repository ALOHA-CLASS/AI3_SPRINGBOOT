package com.aloha.shop.service.users;

import com.aloha.shop.domain.users.User;
import com.aloha.shop.dto.users.UserJoinDto;
import com.aloha.shop.dto.users.UserUpdateDto;

public interface UserService {

    boolean isUsernameAvailable(String username);

    User join(UserJoinDto dto);

    User getByUsername(String username);

    User updateInfo(String username, UserUpdateDto dto);
}
