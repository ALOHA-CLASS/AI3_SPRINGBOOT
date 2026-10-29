package com.aloha.security_querydsl.service;

import com.aloha.security_querydsl.domain.Users;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    // 로그인
    public boolean login(Users user, HttpServletRequest request) throws Exception;

    // 조회
    public Users select(String username) throws Exception;

    // 회원 가입
    public boolean join(Users user) throws Exception;

    // 회원 수정
    public boolean update(Users user) throws Exception;

}
