package com.TaskManagement.Entity;

import java.time.LocalDateTime;

import com.TaskManagement.Enum.SprintState;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="sprints")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sprint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@Column(length=10000)
	private String goal;
	private Long projectId;
	
	private LocalDateTime createdAt=LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private SprintState sprintState;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SprintState getSprintState() {
		return sprintState;
	}

	public void setSprintState(SprintState sprintState) {
		this.sprintState = sprintState;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
	

}

