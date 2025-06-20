package com.aloha.spring_response.dto;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;
    
    // ctrl + . : (quick fix, 추가 작업) 단축키
    // 기본 생성자
    public Person() {
        this.name = "aloha";
        this.age = 20;
    }
    


}
