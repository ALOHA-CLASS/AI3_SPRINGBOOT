package com.aloha.spring_mybatis.dto;

import java.util.Date;

import lombok.Data;

/**
 * Board 
 * - 게시글 정보
 */
@Data
public class Board {
    private int no;    
    private String id;    
    private String title;
    private String writer;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
 