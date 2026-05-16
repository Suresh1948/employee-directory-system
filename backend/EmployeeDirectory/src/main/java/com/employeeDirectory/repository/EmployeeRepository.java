package com.employeeDirectory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeeDirectory.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
	List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

	Optional<Employee> findByEmail(String email);

}