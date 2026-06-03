package com.TaskManagement.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.IssueComment;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Service.IssueService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor

public class IssueController {

		@Autowired
		private IssueService issueService;
		
		@PostMapping("/create")
		public ResponseEntity<Issue> createIssue(@RequestBody Issue issue,
												@RequestParam(required=false) Set<String>labels){
			Issue created = issueService.createIssue(issue, labels);
			return ResponseEntity.ok(created);
		}
		
		@GetMapping("/{id}")
		public ResponseEntity<Issue>getIssueById(@PathVariable Long id){
			return ResponseEntity.ok(issueService.GetIssueById(id));
		}
		
		public ResponseEntity<List<Issue>>getIssueByEmail(@RequestParam String assigneeEmail){
			return ResponseEntity.ok(issueService.getIssueByAssigneeEmail(assigneeEmail));
		}
		
		@GetMapping("/sprint/{sprintId}")
		public ResponseEntity<List<Issue>>getIssueBySprintId(@PathVariable Long sprintId){
			return ResponseEntity.ok(issueService.getIssueBySprintId(sprintId));
		}
		
		@GetMapping("/{issueStatus}")
		public ResponseEntity<List<Issue>>getIssueByStatus(@RequestParam IssueStatus status){
			return ResponseEntity.ok(issueService.getIssueByStatus(status));
		}
		
		
		@PutMapping("/{id}/status")
		public ResponseEntity<Issue>UpdateIssueStatus(@PathVariable Long id, @RequestParam IssueStatus issueStatus){
			return ResponseEntity.ok(issueService.updateIssueStatus(id, issueStatus));
		}
		
		@PostMapping("/comment/{id}")
		public ResponseEntity<IssueComment>addComment(@PathVariable Long issueId,
													  @RequestParam String authoremail,
													  @RequestBody String Body){
			return ResponseEntity.ok(issueService.addComments(issueId, authoremail, Body));
		}
		
		@PostMapping("/sprint")
		public ResponseEntity<Sprint>createSprint(@RequestBody Sprint sprint){
			return ResponseEntity.ok(issueService.createSprint(sprint));
		}
		
		@PostMapping("/search")
		public ResponseEntity<List<Issue>>search(@RequestBody Map<String, String>filters){
			return ResponseEntity.ok(issueService.search(filters));
		}
		
}
