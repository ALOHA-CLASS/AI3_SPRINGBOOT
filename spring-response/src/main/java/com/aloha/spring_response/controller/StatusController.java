package com.aloha.spring_response.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StatusController {

    @GetMapping("/200")
    public ResponseEntity<String> ok() {
        log.info("[GET] - 200 - 서버가 클라이언트의 요청을 성공적으로 처리");
        return ResponseEntity.ok("서버가 클라이언트의 요청을 성공적으로 처리");
    }
 
    @GetMapping("/201")
    public ResponseEntity<String> created() {
        log.info("[GET] - 201 - 서버가 새로운 자원을 성공적을 생성");
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body("서버가 새로운 자원을 성공적을 생성");
    }

    @GetMapping("/400")
    public ResponseEntity<String> badRequest() {
        log.info("[GET] - 400 - 클라이언트의 요청이 잘못됨");
        return ResponseEntity.badRequest().body("클라이언트의 요청이 잘못됨");
    }

    @GetMapping("/403")
    public ResponseEntity<String> forbidden() {
        log.info("[GET] - 403 - 클라이언트의 접근이 거부됨(접근 권한 없음)");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body("클라이언트의 접근이 거부됨(접근 권한 없음)");
    }

    @GetMapping("/404")
    public ResponseEntity<String> notFound() {
        log.info("[GET] - 404 - 자원이 존재하지 않거나, 잘못된 요청경로일 때 상태코드");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body("자원이 존재하지 않거나, 잘못된 요청경로일 때 상태코드");
    }

    @GetMapping("/500")
    public ResponseEntity<String> internalServerError() {
        log.info("[GET] - 500 - 서버 측 에러나 예외 발생");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("서버 측 에러나 예외 발생");
    }
    
}
