package com.tarai.project_management_system_backend.reposritory;

import com.tarai.project_management_system_backend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    public List<Issue> findByProjectId(Long id);
}
