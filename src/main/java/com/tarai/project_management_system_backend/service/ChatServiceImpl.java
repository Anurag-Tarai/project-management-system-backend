package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.Chat;
import com.tarai.project_management_system_backend.reposritory.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatRepository chatRepository;

    @Override
    public Chat creatChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
