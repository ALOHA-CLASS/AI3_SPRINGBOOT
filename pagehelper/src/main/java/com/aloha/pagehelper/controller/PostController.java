package com.aloha.pagehelper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.aloha.pagehelper.domain.Pagination;
import com.aloha.pagehelper.domain.Posts;
import com.aloha.pagehelper.service.PostService;
import com.github.pagehelper.PageInfo;

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

        // PageHelper 통한 페이징처리
        int page = (int) pagination.getPage();
        int size = (int)  pagination.getSize();
        PageInfo<Posts> pageInfo = postService.page(page, size);
        log.info("pageInfo : {}",pageInfo);
        model.addAttribute("pageInfo", pageInfo);

        // Uri 빌더
        String pageUri = UriComponentsBuilder.fromPath("/posts/list")
                                            // Pagination
                                            //  .queryParam("size", pagination.getSize())
                                            //  .queryParam("count", pagination.getCount())
                                            // PageHelper
                                             .queryParam("size", pageInfo.getSize())
                                             .queryParam("count", pageInfo.getPageSize())
                                             .build()
                                             .toUriString();
        model.addAttribute("pageUri", pageUri);
        return "posts/list";

    }
    
    
}
