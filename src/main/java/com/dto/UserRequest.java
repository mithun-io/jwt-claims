package com.dto;

import com.enums.UserRole;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

	@NotBlank(message = "name is required")
	@Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
	private String username;

	@NotBlank(message = "email is required")
	@Email(message = "invalid email format")
	private String email;

	@NotNull(message = "mobile number is required")
	@Digits(fraction = 0, integer = 10)
	private Long mobile;

	@NotBlank(message = "password is required")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character")
	private String password;

	@NotNull(message = "user role is required")
	private UserRole userRole;
}
