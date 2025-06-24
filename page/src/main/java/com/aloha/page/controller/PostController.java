package com.aloha.page.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.aloha.page.domain.Pagination;
import com.aloha.page.domain.Posts;
import com.aloha.page.service.PostService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired private PostService postService;

    @GetMapping("/list")
    public String list(
        Model model,
        // @RequestParam(name = "page", defaultValue = "1") long page,
        // @RequestParam(name = "size", defaultValue = "10") long size,
        // @RequestParam(name = "count", defaultValue = "10") long count
        Pagination pagination
    ) throws Exception {
        // Pagination pagination = new Pagination(page, size, count, 0);
        List<Posts> list = postService.page(pagination);
        model.addAttribute("pagination", pagination);
        model.addAttribute("list", list);

        // Uri 빌더
        String pageUri = UriComponentsBuilder.fromPath("/posts/list")
                                            //  .queryParam("page", pagination.getPage())
                                             .queryParam("size", pagination.getSize())
                                             .queryParam("count", pagination.getCount())
                                             .build()
                                             .toUriString();
        model.addAttribute("pageUri", pageUri);
        return "posts/list";

    }
    
    
}
