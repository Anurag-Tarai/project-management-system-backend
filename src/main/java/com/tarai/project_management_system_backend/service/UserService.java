package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.User;

public interface UserService {

    User findUserProfileByJwt(String jwt)throws Exception;

    User findUserById(Long userId)throws Exception;

    User findUserByEmail(String email)throws Exception;

    User updateUserProjectSize(User user, int number)throws  Exception;
}
