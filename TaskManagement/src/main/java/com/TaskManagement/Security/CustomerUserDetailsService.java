package com.TaskManagement.Security;

import java.awt.List;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.TaskManagement.Entity.UserAuth;
import com.TaskManagement.Enum.Permission;
import com.TaskManagement.Repository.UserAuthRepository;


public abstract class CustomerUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private UserAuthRepository userRepo;
	
	public UserDetails loadUserEmail(String userOfficialEmail)throws Exception {
		
		UserAuth user= userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()->new RuntimeException("user not found"));
		
		Set<Permission> perm= RolePermissionConfig.getRolePermission().get(user.getRole());
		
		return new org.springframework.security.core.userdetails.User(user.getUserOfficialEmail(), user.getPassword(), null);
		
//UserAuth user = userRepo.findByUserOfficialEmail(userOfficialEmail).orElseThrow(()-> new RuntimeException("User not found"));
//		
//		Set<Permission> permission= RolePermissionConfig.getRolePermission().get(user.getRole());
//		
//		List<GrantedAuthority> authorities= (permission == null? 
//				                      Collections.emptyList():permission.
//				                                               stream().
//				                                               map(p-> new SimpleGrantedAuthority(p.name()))
//				                                               .collect(Collectors.toList()));
//		authorities.add(new SimpleGrantedAuthority("Role"+user.getRole().name()));
//		
//		return new org.springframework.security.core.userdetails.User(user.getUserOfficialEmail(),
//				                                                      user.getPassword(),authorities);
		
	}

}


