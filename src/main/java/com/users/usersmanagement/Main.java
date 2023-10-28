package com.users.usersmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
