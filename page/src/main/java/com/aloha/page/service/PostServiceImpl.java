package com.aloha.page.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.page.domain.Pagination;
import com.aloha.page.domain.Posts;
import com.aloha.page.mapper.PostMapper;

@Service
public class PostServiceImpl implements PostService {

    @Autowired private PostMapper postMapper;

    @Override
    public List<Posts> list() throws Exception {
        return postMapper.list();
    }

    @Override
    public Posts select(Integer no) throws Exception {
        return postMapper.select(no);
    }

    @Override
    public boolean insert(Posts post) throws Exception {
        return postMapper.insert(post) > 0;
    }
    
    @Override
    public boolean update(Posts post) throws Exception {
        return postMapper.update(post) > 0;
    }
    
    @Override
    public boolean delete(Integer no) throws Exception {
        return postMapper.delete(no) > 0;
    }

    @Override
    public List<Posts> page(Pagination pagination) throws Exception {
        // 데이터 수 조회
        long total = postMapper.count();
        pagination.setTotal(total);
        return postMapper.page(pagination);
    }
    
}
