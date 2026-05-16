package com.employeeDirectory.service;


import java.util.UUID;
import org.springframework.data.domain.Page;
import com.employeeDirectory.dto.EmployeeDto;
import com.employeeDirectory.entity.User;

public interface EmployeeService {
	EmployeeDto createEmployee(EmployeeDto employeeDto);

	EmployeeDto updateEmployee(UUID id, EmployeeDto employeeDto);

	void deleteEmployee(UUID id);

	EmployeeDto getEmployeeById(UUID id);

	Page<EmployeeDto> getAllEmployees(

			int page, int size

	);

	User findOrCreateGoogleUser(String email, String name);

}
