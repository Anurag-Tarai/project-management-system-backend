package com.tarai.project_management_system_backend.controller;

import com.tarai.project_management_system_backend.config.JwtProvider;
import com.tarai.project_management_system_backend.entity.User;
import com.tarai.project_management_system_backend.reposritory.UserRepository;
import com.tarai.project_management_system_backend.request.LoginRequest;
import com.tarai.project_management_system_backend.response.AuthResponse;
import com.tarai.project_management_system_backend.service.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsImpl customeUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User userInDb = userRepository.findByEmail(user.getEmail());

        if(userInDb!=null) throw new Exception("email already exists with another account");

        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createdUser.setUsername(user.getUsername());
        createdUser.setEmail(user.getEmail());

        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("sign Up successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/singing")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws Exception{
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("sign in successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password){
        UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("invalid username");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
