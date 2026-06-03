package com.TaskManagement.Service;

import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.TaskManagement.DTO.AuthResponse;
import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.RegisterRequest;
import com.TaskManagement.Entity.UserAuth;
import com.TaskManagement.Repository.UserAuthRepository;
import com.TaskManagement.Security.EmailService;
import com.TaskManagement.Security.JWTUtil;
import com.TaskManagement.Security.TokenBlockListService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserAuthService {
	
	@Autowired
	private UserAuthRepository userRepo;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private TokenBlockListService tokenBlockService;
	
	
	public String register(RegisterRequest register) {
		
		if(userRepo.findByUserOfficialEmail(register.userOfficialEmail).isPresent()) {
			throw new RuntimeException("User Already exist");
		}
		UserAuth user = new UserAuth();
		user.setUserName(register.userName);
		user.setUserOfficialEmail(register.userOfficialEmail);
		user.setPassword(passwordEncoder.encode(register.password));
		user.setRole(register.role);
		
		userRepo.save(user);
		
		return "User register successfully";
		
	}
	
	public AuthResponse login(LoginRequestDTO login) {
		
		UserAuth user = userRepo.findByUserOfficialEmail(login.userOfficialEmail)
				              .orElseThrow(()-> new RuntimeException("User not found"));
		
		if(!passwordEncoder.matches(login.password, user.getPassword())) {
			throw new RuntimeException("Invalid Credential");
		}
		
		String token = jwtUtil.generateToken(user);
		
		return new AuthResponse(token,"Login successful");
	}
	
	public void forgotPassword(String userOfficialEmail) {
		
		UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()-> new RuntimeException("user not found"));
		
		String token= UUID.randomUUID().toString();
		
		user.setResetToken(token);
		user.setRestTokenExpire(new Date(System.currentTimeMillis()+10*60*1000));
		
		userRepo.save(user);
		
		emailService.sendResetPasswordMail(userOfficialEmail, token);
		
	}
	public void resetPassword(String token,String newPassword) {
		
		UserAuth user = userRepo.findByResetToken(token).orElseThrow(()-> new RuntimeException("Invalid token"));
		
		if(user.getRestTokenExpire().before(new Date(0))) {
			throw new RuntimeException("Token expired");
		}
		
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetToken(null);
		user.setRestTokenExpire(null);
		
		userRepo.save(user);
	}
	
	public String logOut(HttpServletRequest request) {
		String header= request.getHeader("Authorization");
		String token = jwtUtil.extractToken(header);
		if(token !=null) {
			tokenBlockService.blockListToken(token);
		}
		return "LoggedOut successfully";
	}

}


