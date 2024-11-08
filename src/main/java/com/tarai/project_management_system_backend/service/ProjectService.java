package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.Chat;
import com.tarai.project_management_system_backend.entity.Project;
import com.tarai.project_management_system_backend.entity.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user) throws Exception;

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception;

    Project getProjectById(Long projectId)throws Exception;

    void deleteProject(Long projectId, Long userId) throws  Exception;

    Project updateProject(Long projectId, Project updatedProject)throws Exception;

    void addUserToProject(Long projectId, Long userId)throws  Exception;

    void removeUserFromProject(Long projectId, Long userId)throws  Exception;

    Chat getChatByProjectId(Long projectId)throws Exception;

    List<Project> searchProjects(String keyword, User user) throws Exception;

}