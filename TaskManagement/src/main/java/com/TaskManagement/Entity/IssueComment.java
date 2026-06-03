package com.TaskManagement.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="issue_comments")
@Data
@NoArgsConstructor 
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IssueComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="issue_id",nullable=false)
	private Long issueId;
	
	private String authorEmail;
	
	@Column(length=5000)
	private String body;
	
	private LocalDateTime createdAt = LocalDateTime.now();
	
	
	
}
