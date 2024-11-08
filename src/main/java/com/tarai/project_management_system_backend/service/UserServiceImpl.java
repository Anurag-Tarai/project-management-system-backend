package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.config.JwtProvider;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.reposritory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User>optionalUser= userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new Exception("user not found");
        }
        return optionalUser.get();
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user==null) throw new Exception("user not found");
        return user;
    }

    @Override
    public User updateUserProjectSize(User user, int number) throws Exception {
        user.setProjectSize(number);
        return userRepository.save(user);
    }
}
