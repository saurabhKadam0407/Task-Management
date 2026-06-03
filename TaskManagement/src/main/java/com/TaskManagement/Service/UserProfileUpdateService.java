package com.TaskManagement.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.DTO.UserProfileUpdateDTO;
import com.TaskManagement.Entity.UserProfileUpdate;
import com.TaskManagement.Repository.UserProfileUpdateRepository;


@Service
public class UserProfileUpdateService {
    
    @Autowired
    private UserProfileUpdateRepository userProfileUpdateRepo;
    
    public UserProfileUpdateDTO updateUserProfile(String userOfficialEmail, UserProfileUpdateDTO profileUpdate) {
        
        UserProfileUpdate userProfile = userProfileUpdateRepo
            .findByUserOfficialEmail(userOfficialEmail)
            .orElseGet(() -> {
                // Create new profile if not exists
                UserProfileUpdate newProfile = new UserProfileUpdate();
                newProfile.setUserOfficialEmail(userOfficialEmail);
                return newProfile;
            });
        
        // Update fields
        userProfile.setUserName(profileUpdate.getUserName());
        userProfile.setDepartment(profileUpdate.getDepartment());
        userProfile.setDesignation(profileUpdate.getDesignation());
        userProfile.setOrganizationName(profileUpdate.getOrganizationName());
        userProfile.setActive(profileUpdate.isActive());
        
        userProfileUpdateRepo.save(userProfile);
        return toDTO(userProfile);
    }
    
    public List<UserProfileUpdateDTO> getAllProfile() {
        return userProfileUpdateRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public UserProfileUpdateDTO getUserByEmail(String userOfficialEmail) {
        UserProfileUpdate userProfile = userProfileUpdateRepo
            .findByUserOfficialEmail(userOfficialEmail)
            .orElseThrow(() -> new RuntimeException("User profile not found"));
        return toDTO(userProfile);
    }


    private UserProfileUpdateDTO toDTO(UserProfileUpdate userProfileUpdate) {
        return UserProfileUpdateDTO.builder()
            .id(userProfileUpdate.getId())
            .userName(userProfileUpdate.getUserName())
            .userOfficialEmail(userProfileUpdate.getUserOfficialEmail())
            .department(userProfileUpdate.getDepartment())
            .designation(userProfileUpdate.getDesignation())
            .organizationName(userProfileUpdate.getOrganizationName())
            .active(userProfileUpdate.isActive())
            .build();
    }
}


































//package com.TaskManagement.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.TaskManagement.DTO.UserProfileUpdateDTO;
//import com.TaskManagement.Entity.UserProfileUpdate;
//import com.TaskManagement.Repository.UserProfileUpdateRepository;
//
//
//
//@Service
//public class UserProfileUpdateService {
//	
//	@Autowired
//	private UserProfileUpdateRepository userProfileUpdateRepo;
//	
//	
//	public UserProfileUpdateDTO updateUserProfile(String userOfficialEmail,UserProfileUpdateDTO profileUpdate) {
//		
//
//		UserProfileUpdate userProfile= userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
//				                         .orElseThrow(()-> new RuntimeException("User not found"));
//		
//		
//		userProfile.setUserName(profileUpdate.getUserName());
//		userProfile.setDepartment(profileUpdate.getDepartment());
//		userProfile.setDesignation(profileUpdate.getDesignation());
//		userProfile.setOrganizationName(profileUpdate.getOrganizationName());
//		userProfile.setActive(profileUpdate.isActive());
//		
//		userProfileUpdateRepo.save(userProfile);
//		
//		return toDTO(userProfile);
//		
//	}
//	public List<UserProfileUpdateDTO>getAllProfile(){
//		return userProfileUpdateRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
//	}
//	
//	public UserProfileUpdateDTO getUserByEmail(String userOfficialEmail) {
//		UserProfileUpdate userProfile= userProfileUpdateRepo.findByUserOfficialEmail(userOfficialEmail)
//				                   .orElseThrow(()-> new RuntimeException("user not found"));
//		return toDTO(userProfile);
//	}
//
//	private UserProfileUpdateDTO toDTO(UserProfileUpdate userProfileUpdate) {
//		
//		UserProfileUpdateDTO dto= new UserProfileUpdateDTO();
//		
//		dto.setId(userProfileUpdate.getId());
//		dto.setUserName(userProfileUpdate.getUserName());
//		dto.setUserOfficialEmail(userProfileUpdate.getUserOfficialEmail());
//		dto.setDepartment(userProfileUpdate.getDepartment());
//		dto.setDesignation(userProfileUpdate.getDesignation());
//		dto.setOrganizationName(userProfileUpdate.getOrganizationName());
//		dto.setActive(userProfileUpdate.isActive());
//		
//		return dto;
//		
//		
//	}
//}
//
