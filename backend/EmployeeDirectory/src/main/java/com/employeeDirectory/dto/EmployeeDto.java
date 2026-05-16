package com.employeeDirectory.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class EmployeeDto {

	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private String department;
	private String designation;
	private LocalDate joinedOn;
	private String status;
	private String mobileNo;
	private String address;
}
