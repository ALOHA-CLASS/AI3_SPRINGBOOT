package com.aloha.spring_request.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date regDate;
    private Date updDate;
    private int views;

    public Board() {
        this.id = UUID.randomUUID().toString();
    }
    
    public Board(String title, String writer, String content) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
    
}