package com.TaskManagement.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement.Entity.Issue;
import com.TaskManagement.Entity.Sprint;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.SprintState;
import com.TaskManagement.Repository.IssueRepository;
import com.TaskManagement.Repository.SprintRepository;

@Service
public class SprintService {
	
	@Autowired
	private IssueRepository issueRepo;
	
	@Autowired
	private SprintRepository sprintRepo;
	
	
	public Sprint createSprint(Sprint sprint) {
		sprint.setSprintState(SprintState.PLANNED);
		return sprintRepo.save(sprint);
	}
	
	@Transactional
	public Issue assignIssueToSpring(Long sprintId, Long issueId) {
		Sprint Sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not found"));
		
		Issue issue = issueRepo.findById(issueId).orElseThrow(()->new RuntimeException("Issue not found"));
		
		if(Sprint.getSprintState() == SprintState.COMPLETED) {
			throw new RuntimeException("can not add tasks to completed sprint");
		}
	
		issue.setSprintId(sprintId);
		
		return issueRepo.save(issue);
			
	}
	
	@Transactional
	public Sprint startSprint(Long sprintId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not Found"));
		
		if(sprint.getSprintState() != SprintState.PLANNED) {
			throw new RuntimeException("Sprint can not be started");
		}
		
		sprint.setSprintState(SprintState.ACTIVE);
		
		if(sprint.getStartDate() == null) {
			sprint.setStartDate(LocalDateTime.now());		
			
		}
		return sprintRepo.save(sprint);
		
	}
	
	@Transactional
	public Sprint endSprint(Long sprintId) {
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not Found"));
		sprint.setSprintState(SprintState.COMPLETED);
		
		if(sprint.getEndDate() == null) {
			sprint.setStartDate(LocalDateTime.now());
		}
		
		List<Issue>issues = issueRepo.findBySprintId(sprintId);
		
		for(Issue i :issues) {
			if(!i.getIssueStatus().name().equals(IssueStatus.DONE)) {
				i.setSprintId(null);
				issueRepo.save(i);
			}
		}
		return sprintRepo.save(sprint);
	}
	
	public Map<String, Object> getBurnDownData(Long sprintId){
		Sprint sprint = sprintRepo.findById(sprintId).orElseThrow(()-> new RuntimeException("Sprint not Found"));
		LocalDateTime start = sprint.getStartDate();
		
		LocalDateTime end = sprint.getEndDate() != null?sprint.getEndDate():LocalDateTime.now();
		
		List<Issue> issues = issueRepo.findBySprintId(sprintId);
		
		int totalTask = issues.size();
		
		Map<String, Integer> chart = new LinkedHashMap<String, Integer>();
		LocalDateTime cursor = start;
		
		while(!cursor.isAfter(end)) {
			int completed =(int)issues.stream().filter(i->i.getIssueStatus().name().equals(IssueStatus.DONE)).count();
			
			int remaining = totalTask - completed;
			
			chart.put(cursor.toString(), remaining);
			cursor = cursor.plusDays(1);
		}
		
		Map<String, Object> response = new HashMap<>();
		 response.put("sprintId", sprintId);
		 response.put("startDate", start);
		 response.put("endDate",end);
		 response.put("burnDown", chart);
		 
		 return response;
	}
	
	
}
