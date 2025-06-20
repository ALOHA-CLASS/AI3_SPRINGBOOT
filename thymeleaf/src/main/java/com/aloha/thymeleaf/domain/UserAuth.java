package com.aloha.thymeleaf.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuth {
    private String useranme;
    private String auth;
    
}
