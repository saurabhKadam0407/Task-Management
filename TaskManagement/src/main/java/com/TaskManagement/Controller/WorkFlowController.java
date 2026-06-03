package com.TaskManagement.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Service.WorkFlowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class WorkFlowController {
	
	
	@Autowired
	private WorkFlowService workFlowService;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<WorkFlow>createWorkFlow(@RequestBody WorkFlow workFlow){
		return ResponseEntity.ok(workFlowService.createWorkFlow(workFlow));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<WorkFlow>>getAllList(){
		return ResponseEntity.ok(workFlowService.getAllWork());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<WorkFlow>getWorkById(@PathVariable Long id){
		return ResponseEntity.ok(workFlowService.getWorkById(id));
	}
	
	@PutMapping("/updata/{id}")
	public ResponseEntity<WorkFlow>updateWork(@PathVariable Long id,@RequestBody WorkFlow workFlow){
		return ResponseEntity.ok(workFlowService.updateWorkFlow(id, workFlow));
	}
	
	@GetMapping("/{id}/transactions/{from}")
	public ResponseEntity<Boolean> allowed(@PathVariable Long id,
			@RequestParam IssueStatus from,@RequestParam IssueStatus to,@RequestBody Set<Role>userRole){
		return ResponseEntity.ok(workFlowService.isTransacionAllwed(id, from, to, userRole));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		workFlowService.deleteWork(id);
		return ResponseEntity.ok("Deleted");
	}

}
