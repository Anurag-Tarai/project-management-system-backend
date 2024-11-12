package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.Issue;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId)throws Exception;

    List<Issue> getIssuesByProjectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user)throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId)throws Exception;

    Issue updateStatus(Long issueId, String status) throws  Exception;
}
