package com.tarai.project_management_system_backend.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
