package com.kh.board.service;

import com.kh.board.dto.AuthDto;

public interface AuthService {
    AuthDto.LoginResponse login(AuthDto.LoginRequest request);
}