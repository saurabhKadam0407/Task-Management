package com.TaskManagement.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TaskManagement.Enum.IssueStatus;



@FeignClient(name="issue-service",url="${issue_service.url}")
public interface IssueClient {
	
	
//	@PutMapping("/{id}/status")
	@PutMapping("/api/issues/{id}/status")
	void updateStatus(@PathVariable Long id,
			@RequestParam IssueStatus issueStatus,
			@RequestParam String performBy);

//	@PostMapping("/{id}/comment")
	@PostMapping("/api/issues/comment/{id}")
	void addComments(@PathVariable Long id,
			@RequestParam String author,
			@RequestParam String body);
}

