package com.TaskManagement.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Service.IntegrationService;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationController {
	
	
	@Autowired
	private IntegrationService integrationService;
	
	
	@PostMapping("/push_code")
	public ResponseEntity<?>push_code(@RequestParam String message,@RequestParam String author){
		integrationService.pushCode(message, author);
		return ResponseEntity.ok("Commit processed");
	}
	
	@PostMapping("/pull_request")
	public ResponseEntity<?>pullRequest(@RequestParam String title,@RequestParam String author){
		integrationService.pullRequest(title, author);
		return ResponseEntity.ok("Pull Request Processed");
	}
	
	@PostMapping("/github")
	public ResponseEntity<?>handleGithub(@RequestBody Map<String,Object>payLoad){
		integrationService.handGitHubService(payLoad);
		return ResponseEntity.ok("Github event process successfully");
		
		
	}
	
	
	@PostMapping("/jenkin")
	public ResponseEntity<?>handleJenkins(@RequestBody Map<String,Object>payLoad){
		integrationService.handleJenkinService(payLoad);
		
		return ResponseEntity.ok("jenkins event process successfully");
	}

}
