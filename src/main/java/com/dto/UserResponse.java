package com.dto;

import com.enums.UserRole;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private Long mobile;

    private Boolean isActive;

    private UserRole userRole;
}
