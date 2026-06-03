package com.TaskManagement.Entity;


import com.TaskManagement.Enum.IssueStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="board_columns",indexes={@Index(columnList="board_id,position")})

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class BoardColumn {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	private String name;
	private IssueStatus statusKey;
	private Integer position;
	private Integer wipLimit;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="board_id")
	private Board board;
}
