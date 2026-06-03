package com.TaskManagement.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.Client.IssueClient;
import com.TaskManagement.Enum.IssueStatus;




@Service
public class IntegrationService {
	
	@Autowired
	private IssueClient issueClient;
	
	
	public void pushCode(String message,String author) {
		
		String regex= "([A-Z]+-\\d+)";
		Matcher matcher=Pattern.compile(regex).matcher(message);
		
		if(matcher.find()) {
			Long issueId= Long.parseLong(matcher.group(1).split("-")[1]);
			issueClient.updateStatus(issueId, IssueStatus.DONE, author);
			issueClient.addComments(issueId, author, "Closed via commit"+message);
			
		}
	}
	
	public void pullRequest(String title,String author) {
		
		String regex= "([A-Z]+-\\d+)";
		Matcher matcher= Pattern.compile(regex).matcher(title);
		if(matcher.find()) {
			Long issueId= Long.parseLong(matcher.group(1).split("-")[1]);
			
			issueClient.updateStatus(issueId, IssueStatus.IN_PROGRESS, author);
			issueClient.addComments(issueId, author, "Pull Request opened:"+title);
		}
		
	}
	
	
	
	public void handGitHubService(Map<String,Object>payLoad) {
		
//		List<Map<String,Object>>commitsCodes= (List<Map<String,Object>>)payLoad.get("commitsCodes");
		List<Map<String,Object>>commitsCodes= (List<Map<String,Object>>)payLoad.get("commits");
		
		
		for(Map<String,Object>commit:commitsCodes) {
			String message=(String)commit.get("message");
			String author= ((Map<String,String>)commit.get("author")).get("email");
			
			extractIssueKey(message).ifPresent(issueId-> {
				issueClient.updateStatus(issueId, IssueStatus.DONE, author);
				issueClient.addComments(issueId, author, "Closed By GitHub commit:"+message);
			});
			
		}
		
	}
	
	private Optional<Long>extractIssueKey(String text){
		Matcher m= java.util.regex.Pattern.compile("PROJ-(\\d+)").matcher(text);
		
		return m.find()? Optional.of(Long.valueOf(m.group(1))):Optional.empty();
	}
	

	
	public void handleJenkinService(Map<String,Object>payLoad) {
		
		//String jobName= (String)payLoad.get("jobs");jobName
		String jobName= (String)payLoad.get("jobName");
		String status= (String)payLoad.get("status");
		
		extractIssueKey(jobName).ifPresent(issueId->{
			if("Failure".equals(status)) {
			
			issueClient.addComments(issueId, "jenkin@system", "Build failed for job"+jobName);
			}});
		
	}
}
