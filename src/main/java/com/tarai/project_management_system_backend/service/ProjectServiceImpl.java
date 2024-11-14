package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.Chat;
import com.tarai.project_management_system_backend.entity.Project;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.reposritory.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    UserService userService;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project project1 = new Project();
        project1.setOwner(user);
        project1.setTags(project.getTags());
        project1.setName(project.getName());
        project1.setCategory(project.getCategory());
        project1.setDescription(project.getDescription());
        project1.getTeam().add(user);

        Project savedProject = projectRepository.save(project1);

        Chat chat = new Chat();
        chat.setProject(savedProject);

        Chat projectChat = chatService.creatChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if(category!=null){
            projects = projects.stream().filter(project ->
                    project.getCategory().equals(category))
                    .collect(Collectors.toList());
        }
        if(tag != null){
            projects = projects.stream().filter(project ->
                    project.getTags().contains(tag)).collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("project does not exist");
        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if(optionalProject.isEmpty()){
            throw new Exception("Project not found");
        }
        if(optionalProject.get().getOwner().getId()!=userId)
            throw new Exception("you are not allowed to delete this project");
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Long projectId, Project updatedProject) throws Exception {
        Project project = getProjectById(projectId);
        project.setName(updatedProject.getName());
        project.setTags(updatedProject.getTags());
        project.setDescription(updatedProject.getDescription());
        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
         Project project = getProjectById(projectId);
         User user = userService.findUserById(userId);
         if(!project.getTeam().contains(user)){
             project.getChat().getUsers().add(user);
             project.getTeam().add(user);

         }
        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);
        if(project.getTeam().contains(user)){
            project.getTeam().remove(user);
            project.getChat().getUsers().remove(user);
        }
        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

        return projectRepository.findByNameContainingAndTeamContains(keyword,user);
    }
}
