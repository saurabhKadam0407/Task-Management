package com.TaskManagement.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="work_flows")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkFlow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true,nullable=false)
	private String name;
	
	@Column(length=2000)
	private String descriptions;
	
	private LocalDateTime createdAt= LocalDateTime.now();
	
	@OneToMany(mappedBy="workFlow",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<WorkFlowTransaction>transactions= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<WorkFlowTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<WorkFlowTransaction> transactions) {
		this.transactions = transactions;
	}
	
	
	
}
