package com.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dto.UserRequest;
import com.dto.UserResponse;
import com.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", source = "username")
	@Mapping(target = "userRole", expression = "java(com.enums.UserRole.USER)")
	@Mapping(target = "isActive", constant = "true")
	@Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
//	@Mapping(target = "age", expression = "java(java.time.Period.between(userRequest.getBirthdate(), java.time.LocalDate.now()).getYears())")
	public abstract User toUser(UserRequest userRequest);

	public abstract UserResponse toUserResponse(User user);
	
	public abstract List<UserResponse> toUserResponses(List<User> users);
}
