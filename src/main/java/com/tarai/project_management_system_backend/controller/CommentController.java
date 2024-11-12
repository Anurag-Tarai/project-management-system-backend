package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.entity.Comment;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.request.CreateCommentRequest;
import com.tarai.project_management_system_backend.response.MessageResponse;
import com.tarai.project_management_system_backend.service.CommentService;
import com.tarai.project_management_system_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest req,
                                                 @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.findUserProfileByJwt(jwt.substring(7));
        Comment createdComment = commentService.createComment(req.getIssueId(),
                user.getId(),req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable Long commentId,
                                                 @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.findUserProfileByJwt(jwt.substring(7));
        commentService.deleteComment(commentId, user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("comment deleted successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssuedId(@PathVariable Long issueId)throws Exception{
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
