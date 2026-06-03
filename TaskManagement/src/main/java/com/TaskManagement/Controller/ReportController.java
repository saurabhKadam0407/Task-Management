package com.TaskManagement.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/burnDownData/{sprintId}")
	public ResponseEntity<Map<String,Object>>burnDownData(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportService.burnDownData(sprintId));
	}
	
	@GetMapping("/velocity/{projectId}")
	public ResponseEntity<Map<String,Object>>velocity(@PathVariable Long projectId){
		return ResponseEntity.ok(reportService.velocity(projectId));
	}
	
	@GetMapping("/sprint/{sprintId}")
	public ResponseEntity<Map<String,Object>>sprintReport(@PathVariable Long sprintId){
		return  ResponseEntity.ok(reportService.sprintReport(sprintId));
	}
	
	@GetMapping("/epicReport/{epicId}")
	public ResponseEntity<Map<String,Object>>epicReport(@PathVariable Long epicId){
		
		return ResponseEntity.ok(reportService.epicReport(epicId));
	}
	
	@GetMapping("/workLoad/{sprintId}")
	public ResponseEntity<Map<String,Object>>workLoad(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportService.workLoad(sprintId));
	}
	@GetMapping("/flowChat/{sprintId}")
	public ResponseEntity<Map<String,Object>>flowChart(@PathVariable Long sprintId){
		return ResponseEntity.ok(reportService.flowChat(sprintId));
	}
	

}

