package com.HW.Todo.service;

import com.HW.Todo.exception.InformationExistException;
import com.HW.Todo.model.User;
import com.HW.Todo.model.request.LoginRequest;
import com.HW.Todo.model.response.LoginResponse;
import com.HW.Todo.repository.UserRepository;
import com.HW.Todo.security.JWTUtils;
import com.HW.Todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import java.security.Security;

@Service
public class UserService {
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;



    private UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder,JWTUtils jwtUtils,
                        @Lazy AuthenticationManager authenticationManager,
                        @Lazy MyUserDetails myUserDetails){
        this.userRepository = userRepository;
        this.passwordEncoder =passwordEncoder;
        this.jwtUtils =jwtUtils;
        this.authenticationManager=authenticationManager;
        this.myUserDetails = myUserDetails;

    }

    public User createUser(User userObject){
        System.out.println("service calling create user");
        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())){
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));

            return userRepository.save(userObject);
        }else {
            throw new InformationExistException("User already exist");
        }
    }

    public User findUserByEmailAddress(String email){
        return userRepository.findUserByEmailAddress(email);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),loginRequest.getPassword()
        );
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails =(MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e) {
            return ResponseEntity.ok(new LoginResponse("Error : Username or pass is incorrect"));
        }
    }



}
