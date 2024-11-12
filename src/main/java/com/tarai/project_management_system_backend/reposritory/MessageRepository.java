package com.tarai.project_management_system_backend.reposritory;

import com.tarai.project_management_system_backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
