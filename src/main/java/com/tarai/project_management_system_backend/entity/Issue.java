package com.tarai.project_management_system_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User assignee;
}
