package com.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
