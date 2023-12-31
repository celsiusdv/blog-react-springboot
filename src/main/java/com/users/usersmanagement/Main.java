package com.users.usersmanagement;

import com.users.usersmanagement.entity.Blog;
import com.users.usersmanagement.repository.BlogRepository;
import com.users.usersmanagement.service.BlogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

/*	@Bean
	public CommandLineRunner commandLineRunner(BlogRepository repository){
		return args -> {
			//System.out.println(blogService.createBlog(new Blog("this is a blog test"),1));
			//System.out.println(blogService.searchBlogs("asdf debian hello security"));
			//PageRequest pageRequest=PageRequest.of(0,4);
			//Page<Blog> pages =repository.findAll(pageRequest);
			//System.out.println(pages.getContent());

		};
	}*/
}
