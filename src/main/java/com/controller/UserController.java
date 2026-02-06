package com.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.UserRequest;
import com.dto.UserResponse;
import com.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest) {
	    return ResponseEntity.ok(userService.loginUser(userRequest.getUsername(), userRequest.getPassword()));
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> fetchUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort, @RequestParam(defaultValue = "false") boolean desc) {
		return ResponseEntity.ok(userService.fetchUsers(page, size, sort, desc));
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/users/{id}")
	public ResponseEntity<UserResponse> fetchUserById(@PathVariable long id) {
		return ResponseEntity.ok(userService.fetchUserById(id));
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@GetMapping("/users/username/{username}")
	public ResponseEntity<UserResponse> fetchUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(userService.fetchUserByUsername(username));
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@PutMapping("/users/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.update(id, userRequest));
	}

	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
	@PatchMapping("/users/{id}")
	public ResponseEntity<UserResponse> patchUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.patch(id, userRequest));
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PatchMapping("/users/{id}/deactivate")
	public ResponseEntity<Void> deactivateUser(@PathVariable long id) {
		userService.deactivateUser(id);
		return ResponseEntity.noContent().build();
	}
}
