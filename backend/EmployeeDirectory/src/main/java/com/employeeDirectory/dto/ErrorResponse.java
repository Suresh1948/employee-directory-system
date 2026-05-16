package com.employeeDirectory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

	private LocalDateTime timestamp;

	private int status;

	private String message;
}