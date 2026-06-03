package com.TaskManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.DTO.UserProfileUpdateDTO;
import com.TaskManagement.Service.UserProfileUpdateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user_profile_update")
@RequiredArgsConstructor
public class UserProfileUpdateController {
	
	@Autowired
	private UserProfileUpdateService userProfileService;
	
	
	@PutMapping("/updateUserProfile/{email}")
	public ResponseEntity<UserProfileUpdateDTO>updateUserProfile(@RequestBody UserProfileUpdateDTO userProfile,@PathVariable String email){
		return ResponseEntity.ok(userProfileService.updateUserProfile(email, userProfile));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<UserProfileUpdateDTO>>getAllUser(){
		return ResponseEntity.ok(userProfileService.getAllProfile());
	}
	@GetMapping("/{email}")
	public ResponseEntity<UserProfileUpdateDTO>getUserByEmail(@PathVariable String email){
		return ResponseEntity.ok(userProfileService.getUserByEmail(email));
	}
	

}
