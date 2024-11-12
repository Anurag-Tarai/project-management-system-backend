package com.tarai.project_management_system_backend.request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationRequest {
    private Long projectId;
    private String email;
}
