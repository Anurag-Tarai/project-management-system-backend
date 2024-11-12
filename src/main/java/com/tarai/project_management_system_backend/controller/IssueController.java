package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.dto.IssueDTO;
import com.tarai.project_management_system_backend.entity.Issue;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.request.IssueRequest;
import com.tarai.project_management_system_backend.response.AuthResponse;
import com.tarai.project_management_system_backend.response.MessageResponse;
import com.tarai.project_management_system_backend.service.IssueService;
import com.tarai.project_management_system_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
        return ResponseEntity.ok(issueService.getIssuesByProjectId(projectId));
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issueRequest,
                                                @RequestHeader("Authorization")String token)
        throws Exception{
        System.out.println("issue-----"+issueRequest);
        User tokenUser = userService.findUserProfileByJwt(token.substring(7));

        Issue createIssue = issueService.createIssue(issueRequest, tokenUser);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setDescription(createIssue.getDescription());
        issueDTO.setDueDate(createIssue.getDueDate());
        issueDTO.setId(createIssue.getId());
        issueDTO.setPriority(createIssue.getPriority());
        issueDTO.setProject(createIssue.getProject());
        issueDTO.setProjectId(createIssue.getProjectID());
        issueDTO.setStatus(createIssue.getStatus());
        issueDTO.setTitle(createIssue.getTitle());
        issueDTO.setTags(createIssue.getTags());
        issueDTO.setAssignee(createIssue.getAssignee());


        return ResponseEntity.ok(issueDTO);
    }



    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,
                                                                @RequestHeader("Authorization")String token)
            throws Exception{

        User tokenUser = userService.findUserProfileByJwt(token.substring(7));
        issueService.deleteIssue(issueId, tokenUser.getId());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Issue deleted");

        return ResponseEntity.ok(messageResponse);
    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(@PathVariable Long issueId,
                                                       @PathVariable Long userId)
            throws Exception{

        Issue issue = issueService.addUserToIssue(issueId, userId);

        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(@PathVariable String status,
                                                @PathVariable Long issueId)
            throws Exception{

        Issue issue = issueService.updateStatus(issueId, status);

        return ResponseEntity.ok(issue);
    }

}
