package com.aloha.page.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.aloha.page.domain.Pagination;
import com.aloha.page.domain.Posts;

@Mapper
public interface PostMapper {

    // 목록
    public List<Posts> list() throws Exception;
    // 페이징 목록
    public List<Posts> page(Pagination pagination) throws Exception;
    // 데이터 수
    public long count() throws Exception;
    // 조회
    public Posts select(Integer no) throws Exception;
    // 등록
    public int insert(Posts post) throws Exception;
    // 수정
    public int update(Posts post) throws Exception;
    // 삭제
    public int delete(Integer no) throws Exception;
    
}
