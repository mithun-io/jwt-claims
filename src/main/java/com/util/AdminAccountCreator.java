package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.entity.User;
import com.enums.UserRole;
import com.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminAccountCreator implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${admin.username}")
	private String adminUsername;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.mobile}")
	private Long adminMobile;

	@Value("${admin.password}")
	private String adminPassword;

	@Override
	public void run(String... args) {
		log.info("admin account creation started");

		if (userRepository.existsByEmail(adminEmail)) {
			log.info("admin account already exists");
			return;
		}

		User user = new User();
		user.setUsername(adminUsername);
		user.setEmail(adminEmail);
		user.setMobile(adminMobile);
		user.setPassword(passwordEncoder.encode(adminPassword));
		user.setUserRole(UserRole.ADMIN);
		user.setIsActive(true);

		userRepository.save(user);
		log.info("admin account created successfully");
	}
}
