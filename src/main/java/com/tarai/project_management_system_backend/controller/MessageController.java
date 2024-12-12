package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.entity.Message;
import com.tarai.project_management_system_backend.request.MessageRequest;
import com.tarai.project_management_system_backend.response.MessageResponse;
import com.tarai.project_management_system_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest) {
        try {
            // Log the incoming request
            logger.info("Sending message from user: {} to project: {}", messageRequest.getSenderId(), messageRequest.getProjectId());

            // Call service method to send message
            messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getProjectId(), messageRequest.getContent());

            // Create response object
            MessageResponse response = new MessageResponse("Message sent successfully", messageRequest);

            return ResponseEntity.ok(response);  // Return a more informative response

        } catch (Exception e) {
            // Log the exception
            logger.error("Error sending message", e);
            return ResponseEntity.status(500).body(new MessageResponse("Failed to send message", null));
        }
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChat(@PathVariable Long projectId) {
        try {
            // Log the incoming request
            logger.info("Fetching messages for project: {}", projectId);

            // Get messages from service
            List<Message> messages = messageService.getMessagesByProjectId(projectId);

            if (messages.isEmpty()) {
                logger.info("No messages found for project: {}", projectId);
                return ResponseEntity.noContent().build(); // Return no content if no messages are found
            }

            return ResponseEntity.ok(messages);

        } catch (Exception e) {
            // Log the exception
            logger.error("Error fetching messages for project: {}", projectId, e);
            return ResponseEntity.status(500).build(); // Return a generic server error response
        }
    }
}
