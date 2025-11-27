package com.terabia.terabia;

import com.terabia.terabia.auth.AuthenticationService;
import com.terabia.terabia.auth.RegisterRequest;
import com.terabia.terabia.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TerabiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerabiaApplication.class, args);
	}
	@Autowired
	private UserService userService;

	@Bean
	public CommandLineRunner commandLineRunner(AuthenticationService service, UserService userService) {
		return args -> {
			// Check if admin already exists
			String adminEmail = "admin@gmail.com";
			if (userService.findByEmail(adminEmail).isPresent()) {
				System.out.println("Admin with email " + adminEmail + " already exists.");
			} else {
				var admin = RegisterRequest.builder()
						.firstname("Admin")
						.lastname("Admin")
						.email(adminEmail)
						.password("password")
						.role("ADMIN")
						.build();

				System.out.println("ADMIN token: " + service.register(admin).getToken());
			}

			// Check if manager already exists
			String managerEmail = "manager@gmail.com";
			if (userService.findByEmail(managerEmail).isPresent()) {
				System.out.println("Manager with email " + managerEmail + " already exists.");
			} else {
				var manager = RegisterRequest.builder()
						.firstname("Manager")
						.lastname("Manager")
						.email(managerEmail)
						.password("password")
						.role("MANAGER")
						.build();
				System.out.println("Manager token: " + service.register(manager).getToken());
			}
		};
	}

}