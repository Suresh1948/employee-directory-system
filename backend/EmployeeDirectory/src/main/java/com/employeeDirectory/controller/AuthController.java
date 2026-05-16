package com.employeeDirectory.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.employeeDirectory.dto.AuthResponse;
import com.employeeDirectory.dto.GoogleAuthRequest;
import com.employeeDirectory.dto.LoginRequest;
import com.employeeDirectory.dto.RegisterRequest;
import com.employeeDirectory.entity.User;
import com.employeeDirectory.repository.UserRepository;
import com.employeeDirectory.security.JwtService;
import com.employeeDirectory.service.EmployeeService;
import com.employeeDirectory.service.GoogleTokenVerifierService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final GoogleTokenVerifierService googleTokenVerifierService;
	private final EmployeeService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

		User user = new User();

		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());

		userRepository.save(user);
		return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

		String token = jwtService.generateToken(user.getEmail());

		return new AuthResponse(token, user.getEmail(), user.getRole());
	}

	@PostMapping("/google")
	public ResponseEntity<AuthResponse> googleLogin(@RequestBody GoogleAuthRequest request) throws Exception {

		if (request.getToken() == null || request.getToken().isBlank()) {
			throw new RuntimeException("Google token is missing");
		}
		
		var payload = googleTokenVerifierService.verify(request.getToken());

		if (payload == null) {
			throw new RuntimeException("Invalid Google token");
		}

		String email = payload.getEmail();
		String name = (String) payload.get("name");

		User user = userService.findOrCreateGoogleUser(email, name);

		String jwt = jwtService.generateToken(user.getEmail());

		return ResponseEntity.ok(new AuthResponse(jwt, email, user.getRole()));
	}
}
