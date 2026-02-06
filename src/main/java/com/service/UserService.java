package com.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dto.UserRequest;
import com.dto.UserResponse;
import com.entity.User;
import com.exception.UserAlreadyExistException;
import com.mapper.UserMapper;
import com.repository.UserRepository;
import com.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final UserMapper userMapper;

	public UserResponse register(UserRequest userRequest) {
		if (userRepository.existsByUsername(userRequest.getUsername())) {
			throw new UserAlreadyExistException(userRequest.getUsername() + " already exists");
		}

		User user = userMapper.toUser(userRequest);
		User saved = userRepository.save(user);

		return userMapper.toUserResponse(saved);
	}

	public String loginUser(String username, String password) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new BadCredentialsException("invalid username or password"));

		if (Boolean.FALSE.equals(user.getIsActive())) {
			throw new BadCredentialsException("invalid username or password");
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("invalid username or password");
		}

		return jwtUtil.generateToken(user.getUsername(), List.of(user.getUserRole().name()));
	}

	public List<UserResponse> fetchUsers(int page, int size, String sort, boolean desc) {
		Sort sortBy = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();
		PageRequest pageRequest = PageRequest.of(page - 1, size, sortBy);

		Page<User> pageResult = userRepository.findAll(pageRequest);
		return userMapper.toUserResponses(pageResult.getContent());
	}

	public UserResponse fetchUserById(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found with id " + id));
		return userMapper.toUserResponse(user);
	}

	public UserResponse fetchUserByUsername(String username) {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("user not found with username " + username));
		return userMapper.toUserResponse(user);
	}

	public UserResponse update(long id, UserRequest userRequest) {
		User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found with id " + id));

		user.setUsername(userRequest.getUsername());
		user.setEmail(userRequest.getEmail());
		user.setMobile(userRequest.getMobile());

		User updated = userRepository.save(user);
		return userMapper.toUserResponse(updated);
	}


	public UserResponse patch(long id, UserRequest userRequest) {
		User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found with id " + id));

		if (userRequest.getUsername() != null)
			user.setUsername(userRequest.getUsername());

		if (userRequest.getEmail() != null)
			user.setEmail(userRequest.getEmail());

		if (userRequest.getMobile() != null)
			user.setMobile(userRequest.getMobile());

		User updated = userRepository.save(user);
		return userMapper.toUserResponse(updated);
	}

	public void delete(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found with id " + id));
		userRepository.delete(user);
	}

	public void deactivateUser(long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("user not found with id " + id));
		user.setIsActive(false);
		userRepository.save(user);
	}
}
