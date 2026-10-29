package com.aloha.jpashop.service;

import com.aloha.jpashop.entity.Users;
import com.aloha.jpashop.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    @Override
    public List<Users> findAll() {
        return usersRepository.findAllByJpql();
    }

    @Override
    public List<Users> search(String keyword) {
        return usersRepository.search(keyword);
    }

    @Override
    public Users findByNo(Long no) {
        return usersRepository.findById(no).orElse(null);
    }

    @Override
    public long count() {
        return usersRepository.count();
    }
}
