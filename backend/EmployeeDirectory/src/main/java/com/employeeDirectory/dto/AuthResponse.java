package com.employeeDirectory.dto;

import com.employeeDirectory.entity.Role;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {

	private String token;
	private String email;
	private Role role;

}