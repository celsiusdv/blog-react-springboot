package com.users.usersmanagement.controller;

import com.users.usersmanagement.entity.Blog;
import com.users.usersmanagement.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/blogs")
@CrossOrigin(origins = "http://localhost:3000")
public class BlogController {

    @Autowired private BlogService blogService;

    @PreAuthorize("hasAnyRole('ADMIN_create','USER_create')")
    @PostMapping(path ="/blog")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog){
        System.out.println(blog);
        return new ResponseEntity<>(blogService.createBlog(blog, blog.getUser().getUserId()),HttpStatus.CREATED);
    }
}
