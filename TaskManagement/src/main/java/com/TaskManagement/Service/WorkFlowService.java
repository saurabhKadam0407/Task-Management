package com.TaskManagement.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.TaskManagement.Entity.WorkFlow;
import com.TaskManagement.Entity.WorkFlowTransaction;
import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Repository.WorkFlowRepository;
import com.TaskManagement.Repository.WorkFlowTransactionRepository;

@Service
public class WorkFlowService {

	@Autowired
	private WorkFlowRepository workFllowRepo;
	
	@Autowired
	private WorkFlowTransactionRepository workFlowTransactionRepo;
	
	
	@Transactional
	public WorkFlow createWorkFlow(WorkFlow wf) {
		
		if(wf.getTransactions() !=null) {
			wf.getTransactions().forEach(t-> t.setWorkFlow(wf));
		}
		
		return workFllowRepo.save(wf);
	}
	
	public List<WorkFlow>getAllWork(){
		return workFllowRepo.findAll();
	}
	
	
	public WorkFlow getWorkById(Long id) {
		return workFllowRepo.findById(id).orElseThrow(()-> new RuntimeException("WorkFlow not found "+id));
	}
	
	
	
	@Transactional
	public WorkFlow updateWorkFlow(Long id, WorkFlow updated) {
		
		WorkFlow wf= getWorkById(id);
		
		wf.setName(updated.getName());
		wf.setDescriptions(updated.getDescriptions());
		
		wf.getTransactions().clear();
		
		if(updated.getTransactions() !=null) {
			for(WorkFlowTransaction t:updated.getTransactions()) {
				t.setWorkFlow(wf);
				wf.getTransactions().add(t);
			}
		}
		
		return workFllowRepo.save(wf);
	}
	
	public void deleteWork(Long id) {
		workFllowRepo.deleteById(id);
	}
	
	public boolean isTransacionAllwed(Long workFlowId,IssueStatus from,IssueStatus to,Set<Role>userRole) {
		
		List<WorkFlowTransaction> transactions= workFlowTransactionRepo.findByWorkFlowIdAndFrom(workFlowId, from);
		
		for(WorkFlowTransaction t:transactions ) {
			
			if(!t.getTo().equals(t)) {
				continue;
			}
			
			if(t.getAllowedRole()==null || t.getAllowedRole().isEmpty()) {
				return true;
			}
			
			for(Role role:userRole) {
				if(t.getAllowedRole().contains(userRole)) {
					
					return true;
				}
				
			}
			return false;
		}
		return false;
	}
}

