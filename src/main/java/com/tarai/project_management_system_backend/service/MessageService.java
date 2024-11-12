package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long projectId, String content)throws Exception;

    List<Message> getMessagesByProjectId(Long projectId)throws  Exception;
}
