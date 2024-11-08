package com.tarai.project_management_system_backend.reposritory;

import com.tarai.project_management_system_backend.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
