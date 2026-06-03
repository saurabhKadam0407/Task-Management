package com.TaskManagement.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Service.SprintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sprints")
@RequiredArgsConstructor

public class SprintController {

	@Autowired
	private SprintService sprintService;
	
	@PostMapping("/createSprint")
	public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint){
		return ResponseEntity.ok(sprintService.createSprint(sprint));
		
	}
	
	@PutMapping("assign/{sprintId}/{issueId}")
	public ResponseEntity<Issue> assignIssueToSprint(@PathVariable Long sprintId, @PathVariable Long issueId){
		return ResponseEntity.ok(sprintService.assignIssueToSpring(sprintId, issueId));
	}
	
	@PutMapping("/{sprintId}/start")
	public ResponseEntity<Sprint> startSprint(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.startSprint(sprintId));
	}
	
	
	@PutMapping("/{sprintId}/end")
	public ResponseEntity<Sprint> endSprint(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.endSprint(sprintId));
	}

	@GetMapping("/{sprintId}/burnDownData")
	public ResponseEntity<Map<String,Object>>getBurnDownData(@PathVariable Long sprintId){
		return ResponseEntity.ok(sprintService.getBurnDownData(sprintId));
	}
	
	
}

