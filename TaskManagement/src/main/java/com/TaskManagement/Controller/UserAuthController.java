package com.TaskManagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.DTO.AuthResponse;
import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.RegisterRequest;
import com.TaskManagement.Service.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class UserAuthController {
	
	@Autowired
	private UserAuthService userService;
	
	
	@PostMapping("/register")
	public ResponseEntity<String>register(@RequestBody RegisterRequest register){
		return ResponseEntity.ok(userService.register(register));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse>login(@RequestBody LoginRequestDTO login){
		return ResponseEntity.ok(userService.login(login));
	}
	@PostMapping("/logOut")
	public ResponseEntity<String>logOut(HttpServletRequest request){
		return ResponseEntity.ok(userService.logOut(request));
	}
	@PostMapping("/forgot-password")
	public ResponseEntity<String>forgotPasswrd(@RequestParam String userOfficialEmail){
		userService.forgotPassword(userOfficialEmail);
		return ResponseEntity.ok("Reset password email sent over on your Email");
	}
	@PostMapping("/reset-password")
	public ResponseEntity<String>resetPassword(@RequestParam String token,@RequestParam String newPassword){
		userService.resetPassword(token, newPassword);
		return ResponseEntity.ok("Password Reset successfully");
	}
}
