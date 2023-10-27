package com.users.usersmanagement;

import com.users.usersmanagement.entity.Permission;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.repository.PermissionRepository;
import com.users.usersmanagement.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

/*	@Bean
	public CommandLineRunner commandLineRunner(PermissionRepository permissionRepository){
		return args -> {
			Set<Permission> list= new HashSet<>(permissionRepository.findAll());
			System.out.println(Arrays.toString(list.toArray()));
		};
	}*/
}
