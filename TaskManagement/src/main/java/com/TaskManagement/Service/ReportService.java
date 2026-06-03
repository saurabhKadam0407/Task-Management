package com.TaskManagement.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.SprintRepository;

@Service
public class ReportService {

	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	
	public Map<String,Object>burnDownData(Long sprintId){
		Sprint sprint= sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not found"));
		List<Issue>issues= issueRepo.findBySprintId(sprintId);
		
		
		int totalIssues= issues.size();
		
		Map<String,Object> chart= new LinkedHashMap<>();
		
		LocalDateTime start= sprint.getStartDate();
		LocalDateTime end= sprint.getEndDate() !=null?sprint.getEndDate():LocalDateTime.now();
		
		for(LocalDateTime d = start; !d.isAfter(end);d= d.plusDays(1)) {
			int done=(int)issues.stream().filter(i->i.getIssueStatus()==IssueStatus.DONE).count();
			
			chart.put(d.toString(),totalIssues-done);
		}
		
	Map<String,Object>response= new HashMap<>();
	
	response.put("sprintId", sprintId);
	response.put("burnDownData", chart);
	
	return response;
	
		
	}
	
	
	
	
	public Map<String,Object>velocity(Long projectId){
		
		List<Sprint>completed= sprintRepo.findByProjectId(projectId).stream()
				               .filter(s-> s.getSprintState()==SprintState.COMPLETED).collect(Collectors.toList());
		
		Map<String,Integer> velocity=new LinkedHashMap<>();
		
		for(Sprint sprint:completed) {
			int done= (int)issueRepo.findBySprintId(sprint.getId()).stream()
					  .filter(i-> i.getIssueStatus()==IssueStatus.DONE).count();
			
			velocity.put(sprint.getName(), done);
		}
		
		Map<String,Object>response= new HashMap<>();
		response.put("projectId", projectId);
		response.put("velocity",velocity );
		
		return response;
		
		
	}
	
	public Map<String,Object>sprintReport(Long sprintId){
		List<Issue>issue= issueRepo.findBySprintId(sprintId);
		
		long completed= issue.stream().filter(i->i.getIssueStatus()==IssueStatus.DONE).count();
		
		long incomplete= issue.size()-completed;
		
		Map<String,Object>response=new HashMap<>();
		response.put("sprintId", sprintId);
		response.put("completed", completed);
		response.put("Incomplete",incomplete );
		
		return response;
	}
	
	
	public Map<String,Object>epicReport(Long epicId){
		
		List<Issue>stories= issueRepo.findByEpicId(epicId);
		
		long done= stories.stream().filter(i->i.getIssueStatus()==IssueStatus.DONE).count();
		
		long inprogress= stories.isEmpty()?0:(done*100/stories.size());
		
		Map<String,Object>response= new HashMap<>();
		
		response.put("epicId", epicId);
		response.put("totalStories", stories.size());
		response.put("completedStories", done);
		response.put("progresspercent", inprogress);
		
		return response;
		
		
		
		
	}
	
	
	public Map<String,Object>workLoad(Long sprintId){
		
		List<Issue>issues= issueRepo.findBySprintId(sprintId);
		
		 Map<String,Long>load= issues.stream().collect(Collectors.groupingBy(Issue::getAssigneeEmail,Collectors.counting()));
		 
		 Map<String,Object>response= new HashMap<>();
		 response.put("workLoad", load);
		 
		 return response;
		 
		 
		 
		 
	}
	
	
	public Map<String,Object>flowChat(Long sprintId){
		
		List<Issue>issues= issueRepo.findBySprintId(sprintId);
		
		Map<String,Long>flow= issues.stream().collect(Collectors.groupingBy(issue-> issue.getIssueStatus().name(),Collectors.counting()));
		
		Map<String,Object>response= new HashMap<>();
		response.put("flowChat",flow );
		
		
		return response;
		
		
	}
}

