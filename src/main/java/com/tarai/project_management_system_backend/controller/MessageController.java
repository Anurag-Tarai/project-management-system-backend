package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.entity.Message;
import com.tarai.project_management_system_backend.request.MessageRequest;
import com.tarai.project_management_system_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<MessageRequest>sendMessage(@RequestBody MessageRequest messageRequest) throws Exception {
        messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getProjectId(),messageRequest.getContent());
        return new ResponseEntity<>(messageRequest, HttpStatus.OK);
    }
}
