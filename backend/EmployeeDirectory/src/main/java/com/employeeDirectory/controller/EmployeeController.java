package com.employeeDirectory.controller;

import lombok.RequiredArgsConstructor;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.employeeDirectory.dto.EmployeeDto;
import com.employeeDirectory.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	// ADMIN only
	@PostMapping("/new")
	@PreAuthorize("hasRole('ADMIN')")
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {

		return employeeService.createEmployee(employeeDto);
	}

	// UPDATE EMPLOYEE
	// ADMIN only
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public EmployeeDto updateEmployee(@PathVariable UUID id, @RequestBody EmployeeDto employeeDto) {

		return employeeService.updateEmployee(id, employeeDto);
	}

	// DELETE EMPLOYEE
	// ADMIN only
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {

		employeeService.deleteEmployee(id);

		return ResponseEntity.ok("Employee deleted successfully");
	}

	// GET EMPLOYEE BY ID
	// ADMIN and USER
	@GetMapping("/getById/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public EmployeeDto getEmployeeById(@PathVariable UUID id) {

		return employeeService.getEmployeeById(id);
	}

	// GET EMPLOYEES WITH
	// PAGINATION
	@GetMapping("/page")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Page<EmployeeDto> getAllEmployees(

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size

	) {

		return employeeService.getAllEmployees(

				page, size

		);
	}

}
