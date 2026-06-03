package com.TaskManagement.Entity;


import java.util.HashSet;
import java.util.Set;

import com.TaskManagement.Enum.IssueStatus;
import com.TaskManagement.Enum.Role;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
    name = "workflow_transactions",
    indexes = {
        @Index(
            name = "idx_wf_from_to",
            columnList = "workFlow_id,from_status,to_status"
        )
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WorkFlowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "from_status", nullable = false)
    private IssueStatus from;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "to_status", nullable = false)
    private IssueStatus to;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workFlow_id", nullable = false)
    private WorkFlow workFlow;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "workflow_transaction_roles",
        joinColumns = @JoinColumn(name = "workflow_transaction_id")
    )
    @Column(name = "role")
    private Set<Role> allowedRole = new HashSet<>();
}
