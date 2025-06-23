package com.aloha.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.rest.dto.Posts;
import com.aloha.rest.service.PostService;

import lombok.extern.slf4j.Slf4j;

/**
 * 💚 Restful
 * 👨‍💻 게시판 REST 컨트롤러
 * - 목록   - [GET]         /posts      📨 (param)
 * - 조회   - [GET]         /posts/10   📨 (param)
 * - 등록   - [POST]        /posts      💌 (body)
 * - 수정   - [PUT]         /posts      💌 (body)
 * - 삭제   - [DELETE]      /posts/10   📨 (param)
 */
@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    // sp-crud  💚 spring code generator 확장으로 REST 컨트롤러 자동 완성

    @Autowired private PostService postService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            // 데이터 요청
            List<Posts> list = postService.list();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{no}")
    public ResponseEntity<?> getOne(@PathVariable("no") Integer no) {
        try {
            Posts post = postService.select(no);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody Posts post) {
        try {
            boolean result = postService.insert(post);
            if( result) 
                // 201
                return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
            // 400
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Posts post) {
        try {
            boolean result = postService.update(post);
            if( result) 
                // 200
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            // 400
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{no}")
    public ResponseEntity<?> destroy(@PathVariable("no") Integer no) {
        try {
            boolean result = postService.delete(no);
            if( result) 
                // 200
                return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
            // 400
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // 500
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
