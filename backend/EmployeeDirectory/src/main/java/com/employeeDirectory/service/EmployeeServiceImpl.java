package com.employeeDirectory.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.employeeDirectory.customException.ResourceNotFoundException;
import com.employeeDirectory.dto.EmployeeDto;
import com.employeeDirectory.entity.AuthProvider;
import com.employeeDirectory.entity.Employee;
import com.employeeDirectory.entity.Role;
import com.employeeDirectory.entity.User;
import com.employeeDirectory.repository.EmployeeRepository;
import com.employeeDirectory.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final UserRepository userRepository;

	@Override
	public EmployeeDto createEmployee(EmployeeDto dto) {

		Employee employee = mapToEntity(dto);

		Employee savedEmployee = employeeRepository.save(employee);

		return mapToDto(savedEmployee);
	}

	@Override
	public EmployeeDto updateEmployee(UUID id, EmployeeDto dto) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		employee.setFirstName(dto.getFirstName());
		employee.setLastName(dto.getLastName());
		employee.setEmail(dto.getEmail());
		employee.setDepartment(dto.getDepartment());
		employee.setDesignation(dto.getDesignation());
		employee.setJoinedOn(dto.getJoinedOn());
		employee.setStatus(dto.getStatus());

		Employee updatedEmployee = employeeRepository.save(employee);

		return mapToDto(updatedEmployee);
	}

	@Override
	public void deleteEmployee(UUID id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		employeeRepository.delete(employee);
	}

	@Override
	public EmployeeDto getEmployeeById(UUID id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id : " + id));

		return mapToDto(employee);
	}

	@Override
	public Page<EmployeeDto> getAllEmployees(int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Employee> employees;

		employees = employeeRepository.findAll(pageable);

		return employees.map(this::mapToDto);
	}

	public User findOrCreateGoogleUser(String email, String name) {

		return userRepository.findByEmail(email).orElseGet(() -> {
			User user = new User();
			user.setEmail(email);
			user.setName(name);
			user.setRole(Role.USER);
			user.setProvider(AuthProvider.GOOGLE);
			return userRepository.save(user);
		});
	}

	private EmployeeDto mapToDto(Employee employee) {

		EmployeeDto dto = new EmployeeDto();

		dto.setId(employee.getId());
		dto.setFirstName(employee.getFirstName());
		dto.setLastName(employee.getLastName());
		dto.setEmail(employee.getEmail());
		dto.setDepartment(employee.getDepartment());
		dto.setDesignation(employee.getDesignation());
		dto.setJoinedOn(employee.getJoinedOn());
		dto.setStatus(employee.getStatus());
		dto.setAddress(employee.getAddress());
		dto.setMobileNo(employee.getMobileNo());

		return dto;
	}

	private Employee mapToEntity(EmployeeDto dto) {

		Employee employee = new Employee();

		employee.setId(dto.getId());
		employee.setFirstName(dto.getFirstName());
		employee.setLastName(dto.getLastName());
		employee.setEmail(dto.getEmail());
		employee.setDepartment(dto.getDepartment());
		employee.setDesignation(dto.getDesignation());
		employee.setJoinedOn(dto.getJoinedOn());
		employee.setStatus(dto.getStatus());
		employee.setAddress(dto.getAddress());
		employee.setMobileNo(dto.getMobileNo());

		return employee;
	}

}
