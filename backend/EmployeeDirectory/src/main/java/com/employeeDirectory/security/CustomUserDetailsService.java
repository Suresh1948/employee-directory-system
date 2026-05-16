package com.employeeDirectory.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.employeeDirectory.repository.UserRepository;
import com.employeeDirectory.entity.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		String password = (user.getPassword() != null) ? user.getPassword() : "";

		return new org.springframework.security.core.userdetails.User(user.getEmail(), password,
				List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
	}
}
