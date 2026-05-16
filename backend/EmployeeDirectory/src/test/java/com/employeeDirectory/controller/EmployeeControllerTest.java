package com.employeeDirectory.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import com.employeeDirectory.dto.EmployeeDto;
import com.employeeDirectory.security.CustomUserDetailsService;
import com.employeeDirectory.security.JwtService;
import com.employeeDirectory.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;
	
	@MockBean
	private JwtService jwtService;
	
	@MockBean
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateEmployee() throws Exception {

		EmployeeDto dto = new EmployeeDto();
		dto.setFirstName("pranav");
		dto.setEmail("pranav@test.com");

		when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(dto);

		mockMvc.perform(post("/api/employees/new").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("pranav"));
	}

	@Test
	void testGetEmployeeById() throws Exception {

		UUID id = UUID.randomUUID();

		EmployeeDto dto = new EmployeeDto();
		dto.setFirstName("Suresh");

		when(employeeService.getEmployeeById(any(UUID.class))).thenReturn(dto);
		mockMvc.perform(get("/api/employees/getById/" + id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Suresh"));
	}

	@Test
	void testDeleteEmployee() throws Exception {

		UUID id = UUID.randomUUID();

		doNothing().when(employeeService).deleteEmployee(id);

		mockMvc.perform(delete("/api/employees/delete/" + id)).andExpect(status().isOk())
				.andExpect(content().string("Employee deleted successfully"));
	}

	@Test
	void testGetEmployeesPage() throws Exception {

		Page<EmployeeDto> page = new PageImpl<>(List.of(new EmployeeDto()));
		when(employeeService.getAllEmployees(0, 10)).thenReturn(page);

		mockMvc.perform(get("/api/employees/page?page=0&size=10")).andExpect(status().isOk());
	}

	@Test
	void testUpdateEmployee() throws Exception {

		UUID id = UUID.randomUUID();

		EmployeeDto dto = new EmployeeDto();
		dto.setFirstName("Nivin");

		 when(employeeService.updateEmployee(
		            eq(id),
		            any(EmployeeDto.class)
		    )).thenReturn(dto);

		mockMvc.perform(put("/api/employees/edit/" + id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Nivin"));
	}
}