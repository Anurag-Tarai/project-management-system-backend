package com.tarai.project_management_system_backend.reposritory;

import com.tarai.project_management_system_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
