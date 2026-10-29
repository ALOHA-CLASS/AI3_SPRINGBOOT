package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.Users;

import java.util.List;

public interface UserService {

    List<Users> findAll();

    List<Users> search(String keyword);

    Users findByNo(Long no);

    long count();
}
