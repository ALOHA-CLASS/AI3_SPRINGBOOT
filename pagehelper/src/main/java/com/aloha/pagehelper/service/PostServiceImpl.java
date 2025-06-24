package com.aloha.pagehelper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aloha.pagehelper.domain.Pagination;
import com.aloha.pagehelper.domain.Posts;
import com.aloha.pagehelper.mapper.PostMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

    /**
     * ⭐ PageHelper 페이징 목록
     */
    @Override
    public PageInfo<Posts> page(int page, int size) throws Exception {
        // PageHelper.startPage(현재 번호, 페이지당 데이터 수)
        PageHelper.startPage(page, size);
        List<Posts> list = postMapper.list();

        // ⭐ PageInfo<DTO>( 리스트, 노출 페이지 수 )
        PageInfo<Posts> pageInfo = new PageInfo<>(list, 10);
        return pageInfo;
    }
    
}
