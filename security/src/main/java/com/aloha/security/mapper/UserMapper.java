package com.aloha.security.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.security.dto.UserAuth;
import com.aloha.security.dto.Users;

@Mapper
public interface UserMapper {

    // 회원 조회
    public Users select(String id) throws Exception;

    // 회원 가입
    public int join(Users user) throws Exception;

    // 회원 수정
    public int update(Users user) throws Exception;

    // 회원 권한 등록
    public int insertAuth(UserAuth userAuth) throws Exception;

}
