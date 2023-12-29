package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.Blog;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.BlogRepository;
import com.users.usersmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Blog> getBlogs(){
        if(blogRepository.findAll().isEmpty()) throw new RuntimeException("empty list of blogs");
        else return blogRepository.findAll();
    }
    public List<Blog> searchBlogs(){
        return null;
    }

    public Blog getBlog(Integer id){
        Optional<Blog> blog=blogRepository.findById(id);
        return blog.isPresent() ?
                blog.get()
                : blog.orElseThrow(() -> new RuntimeException("blog not found"));

    }

    @Transactional
    public Blog createBlog(Blog blog, Integer id){
        blog.setUser(userRepository.findById(id).get());
        blogRepository.save(blog);
        System.out.println(blog.getUser());
        return blog;
    }

    public Blog editBlog(Blog blog){
        try{
            Optional<Blog> blogToUpdate= Optional.of(
                    blogRepository.getReferenceById(blog.getBlogId()));//blog from database to be replaced with new values
            log.warn("old blog to be updated: "+blogToUpdate.get());
            blogToUpdate.get().setTitle(blog.getTitle());
            blogToUpdate.get().setPost(blog.getPost());//getUsername returns an email
            log.info("updated: "+blogToUpdate.get());
            return blogRepository.save(blogToUpdate.get());
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean deleteBlog(Integer id){
        try{
            blogRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
