package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.Blog;
import com.users.usersmanagement.repository.BlogRepository;
import com.users.usersmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Blog createBlog(Blog blog, Integer id){
        blog.setUser(userRepository.findById(id).get());
        blogRepository.save(blog);
        System.out.println(blog.getUser());
        return blog;
    }
}
