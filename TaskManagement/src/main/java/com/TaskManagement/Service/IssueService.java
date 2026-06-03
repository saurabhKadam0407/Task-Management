package com.TaskManagement.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.IssueComment;
import com.TaskManagement.Entity.Label;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssuePriority;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.IssueType;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueCommentRepository;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.LabelRepository;
import com.TaskManagement.Repository.SprintRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IssueService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private IssueCommentRepository issueCommentRepo;

	@Autowired
	private LabelRepository labelRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	private String generateKey(Long id) {
		return "PROJECT-"+id;
	}
	
	@Transactional
	public Issue createIssue(Issue issue,Set<String>labelName) {
		
		issue.setIssueType(issue.getIssueType()!=null?issue.getIssueType():IssueType.TASK);
		issue.setPriority(issue.getPriority()!=null?issue.getPriority():IssuePriority.MEDIUM);
		issue.setIssueStatus(IssueStatus.OPEN);
		
		if(labelName!=null) {
			for(String name: labelName) {
				Label label = labelRepo.findByName(name).orElseThrow(()->new RuntimeException("Label not found"));
				issue.getLabels().add(label);
			}
		}
		Issue saved = issueRepo.save(issue);
		saved.setIssueKey(generateKey(saved.getId()));
		return issueRepo.save(saved);
	}
	
	public Issue GetIssueById(Long id) {
		return issueRepo.findById(id).orElseThrow(()-> new RuntimeException("Issue not found"));
	}
	
	public List<Issue> getIssueByAssigneeEmail(String assigneeEmail){
		return issueRepo.findByAssigneeEmail(assigneeEmail);
	}
	
	public List<Issue> getIssueBySprintId(Long sprintId){
		return issueRepo.findBySprintId(sprintId);
	}
	public List<Issue> getIssueByStatus(IssueStatus status){
		return issueRepo.findByIssueStatus(status);
	}
	
	@Transactional
	public IssueComment addComments(Long issueId, String authorEmail,String body) {
		
		Issue issue = GetIssueById(issueId);
		
		IssueComment comments = new IssueComment();
		comments.setIssueId(issue.getId());
		comments.setAuthorEmail(authorEmail);
		comments.setBody(body);
		
		return issueCommentRepo.save(comments);
	}
	
	@Transactional
	public Issue updateIssueStatus(Long id, IssueStatus issueStatus) {
		
		Issue issue = GetIssueById(id);
		
		if(issueStatus == null) {
			throw new RuntimeException("Status can not be null");
		}
		
		issue.setIssueStatus(issueStatus);
		issue.getUpdateAt();
		return issueRepo.save(issue);
	}
	
	@Transactional
	public Sprint createSprint(Sprint sprint) {
		if(sprint.getSprintState() == null) {
			sprint.setSprintState(SprintState.PLANNED);
		}
		return sprintRepo.save(sprint);
	}
	
	public List<Issue> search(Map<String,String>filters){
		
		List<Issue> issue = issueRepo.findAll();
		
		if(filters.containsKey("assigneeEmail")) {
			String email = filters.get("assigneeEmail");
			issue = issue.stream().filter(i->email.equalsIgnoreCase(i.getAssigneeEmail())).collect(Collectors.toList());
			
		}
		if(filters.containsKey("sprint")) {
			Long sprintId = Long.valueOf(filters.get("sprint"));
			issue = issue.stream().filter(i->sprintId.equals(i.getSprintId())).collect(Collectors.toList());
		}
		
//		String statusStr = filters.get("status");
//		if(statusStr != null && statusStr.trim().isEmpty()) {
//			try {
//				IssueStatus issueStatus = IssueStatus.valueOf(statusStr.trim().toUpperCase());
//				
//				issue = issue.stream().filter(i->i.getIssueStatus()==issueStatus).collect(Collectors.toList());
//			}catch(Exception e) {
//				throw new RuntimeException("Invalid Status filter");
//			}
//		}
		
		String statusStr = filters.get("status");
		if (statusStr != null && !statusStr.trim().isEmpty()) {  // Fixed condition
			try {
				IssueStatus issueStatus = IssueStatus.valueOf(statusStr.trim().toUpperCase());
				issue = issue.stream()
					.filter(i -> i.getIssueStatus() == issueStatus)
					.collect(Collectors.toList());
			} catch (Exception e) {
				throw new RuntimeException("Invalid Status filter");
			}
		}
		
		return issue;
	}
	
}

