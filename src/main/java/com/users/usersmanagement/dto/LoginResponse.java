package com.users.usersmanagement.dto;

import com.users.usersmanagement.entity.User;
public record LoginResponse(User user, String accessToken, String refreshToken) {
}
