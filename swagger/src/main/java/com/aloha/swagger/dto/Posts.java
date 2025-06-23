package com.aloha.swagger.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts {

    @Schema(description = "글 번호 입니다. (GET, PUT, DELETE 요청 시, 필요)")
    private Long no;
    @Schema(description = "UK 입니다. (GET, PUT, DELETE 요청 시, 필요)")
    private String id;
    @Schema(description = "제목 입니다. (POST, PUT 요청 시, 필요)")
    private String title;
    @Schema(description = "작성자 입니다. (POST, PUT 요청 시, 필요)")
    private String writer;
    @Schema(description = "내용 입니다. (POST, PUT 요청 시, 필요)")
    private String content;
    @Schema(description = "등록일자 입니다. (현재 시각으로 등록됨)")
    private Date createdAt;
    @Schema(description = "수정일자 입니다. (수정 시각으로 등록됨)")
    private Date updatedAt;
    
}
