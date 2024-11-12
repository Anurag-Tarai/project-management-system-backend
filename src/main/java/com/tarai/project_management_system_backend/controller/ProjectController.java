package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.entity.*;
import com.tarai.project_management_system_backend.reposritory.ProjectRepository;
import com.tarai.project_management_system_backend.request.InvitationRequest;
import com.tarai.project_management_system_backend.response.MessageResponse;
import com.tarai.project_management_system_backend.service.InvitationService;
import com.tarai.project_management_system_backend.service.ProjectService;
import com.tarai.project_management_system_backend.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@RequestParam(required = false)String category,
                                                     @RequestParam(required = false)String tag,
                                                     @RequestHeader("Authorization")String authHeader) throws Exception {
        User user = userService.findUserProfileByJwt(authHeader.substring(7));
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
            @RequestHeader("Authorization")String authHeader, @PathVariable Long projectId) throws Exception {
        String jwt = authHeader.substring(7);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/create-project")
    public ResponseEntity<Project> createProject(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Project project) throws Exception {
        // Extract the token from the Authorization header
//        String jwt = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        String jwt = authHeader.substring(7);
        // Process the token
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project, user);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }


    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @RequestHeader("Authorization")String jwt,
            @RequestBody Project newProject,
            @PathVariable Long projectId) throws Exception {

        Project updatedProject = projectService.updateProject( projectId,newProject);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long projectId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId, user.getId());
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Project Deleted successfully!");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(@RequestParam(required = false)String keyword,
                                                     @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProjects(keyword, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(
            @RequestHeader("Authorization")String jwt,
            @PathVariable Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    // Invitation API's
    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
            @RequestBody InvitationRequest invitationRequest,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Project project) throws Exception {
            invitationService.sendInvitation(invitationRequest.getEmail(),invitationRequest.getProjectId());
            MessageResponse response = new MessageResponse("User invitation sent...");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/accept-invitation")
    public ResponseEntity<Invitation> acceptInvitationProject(
            @RequestParam String token,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Project project) throws Exception {
        String jwt = authHeader.substring(7);
        // Process the token
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token,user.getId());
        projectService.addUserToProject(invitation.getProjectId(), user.getId());
        return new ResponseEntity<>(invitation,HttpStatus.OK);
    }
}
