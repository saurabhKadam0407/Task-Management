package com.TaskManagement.DTO;

import com.TaskManagement.Enum.Role;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequest {

	public String userName;
	public String userOfficialEmail;
	public String password;
	public Role role;
	
	
	
}
