package com.tarai.project_management_system_backend.service;

import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.reposritory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException("user not found with email "+ username);
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorityList);
    }
}
