package com.aloha.spring_response.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    private int no;
    private String id;
    private String title;
    private String writer;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    
    public Board(int no, String title, String writer, String content) {
        this.no = no;
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
