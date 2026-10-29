package com.aloha.security.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Board {
  private Long no;
  @Builder.Default
  private String id = UUID.randomUUID().toString();
  private String title;
  private Long userNo;
  private String content;
  private Date createdAt;
  private Date updatedAt;

  // ⭐ board:user = 1:1 매핑
  private Users user;
  
  
  public Board() {
    this.id = UUID.randomUUID().toString();
  }
}
