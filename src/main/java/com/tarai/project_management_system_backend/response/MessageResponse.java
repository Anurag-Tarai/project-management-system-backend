package com.tarai.project_management_system_backend.response;

import com.tarai.project_management_system_backend.request.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String message;
    private MessageRequest messageRequest;

    // Constructor for only a message
    public MessageResponse(String message) {
        this.message = message;
    }
}
