package com.TaskManagement.Entity;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.TaskManagement.Enum.IssuePriority;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.IssueType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="issues",indexes= {@Index(name="idx_issue_key",columnList="issueKey"),
		                        @Index(name="idx_assignee_email",columnList="assigneeEmail")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true,nullable=false)
	private String issueKey;
	
	@Column(nullable=false)
	private String issueTitle;
	
	@Column(length=5000)
	private String issueDescription;
	
	@Enumerated(EnumType.STRING)
	private IssueType issueType;
	
	@Enumerated(EnumType.STRING)
	private IssuePriority priority;
	
	@Enumerated(EnumType.STRING)
	private IssueStatus issueStatus;
	
	private String assigneeEmail;
	private String reportEmail;
	
	private Long sprintId;
	private Long epicId;
	
	private LocalDateTime createdAt=LocalDateTime.now();
	private LocalDateTime updateAt= LocalDateTime.now();
	private LocalDateTime dueDate;
	
	private Long projectId;
	private Long parentIssueId;
	private Integer backLogPosition;
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="issue_labels",
	           joinColumns=@JoinColumn(name="issue_id"),
	           inverseJoinColumns=@JoinColumn(name="label_id"))
	private Set<Label>labels=new HashSet<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIssueKey() {
		return issueKey;
	}
	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}
	public String getIssueTitle() {
		return issueTitle;
	}
	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	public IssueType getIssueType() {
		return issueType;
	}
	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}
	public IssuePriority getPriority() {
		return priority;
	}
	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}
	public IssueStatus getIssueStatus() {
		return issueStatus;
	}
	public void setIssueStatus(IssueStatus issueStatus) {
		this.issueStatus = issueStatus;
	}
	public String getAssigneeEmail() {
		return assigneeEmail;
	}
	public void setAssigneeEmail(String assigneeEmail) {
		this.assigneeEmail = assigneeEmail;
	}
	public String getReportEmail() {
		return reportEmail;
	}
	public void setReportEmail(String reportEmail) {
		this.reportEmail = reportEmail;
	}
	public Long getSprintId() {
		return sprintId;
	}
	public void setSprintId(Long sprintId) {
		this.sprintId = sprintId;
	}
	public Long getEpicId() {
		return epicId;
	}
	public void setEpicId(Long epicId) {
		this.epicId = epicId;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	public Set<Label> getLabels() {
		return labels;
	}
	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	public Long getParentIssueId() {
		return parentIssueId;
	}
	public void setParentIssueId(Long parentIssueId) {
		this.parentIssueId = parentIssueId;
	}
	public Integer getBackLogPosition() {
		return backLogPosition;
	}
	public void setBackLogPosition(Integer backLogPosition) {
		this.backLogPosition = backLogPosition;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
	
			
	

}
