package com.movies2c.backend.service;

import com.movies2c.backend.model.SignupRequest;
import com.movies2c.backend.model.User;
import com.movies2c.backend.model.LoginRequest;
import com.movies2c.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Time;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthenticationService(UserRepository userRepository){
        this.userRepository=userRepository;

    }

    public static String hash(String password){
       return encoder.encode(password);

    }

    public User createUser(SignupRequest signupRequest){
        User user = new User();
        user.setUserName(signupRequest.getUserName());
        user.setEmail(signupRequest.getEmail());
        String passwordHash=hash(signupRequest.getPassword());
        // System.out.println(passwordHash);
        user.setPasswordHash(passwordHash);
        user.setDate(System.currentTimeMillis());
//        System.out.println(user.getUserName());
//        System.out.println(user.getEmail());
//        System.out.println(user.getPasswordHash());
//        System.out.println(user.getDate());


        return userRepository.save(user);
    }

    public ResponseEntity<?> deleteUser(String id){
        if(!userRepository.existsById(id)){
            return  ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return  ResponseEntity.ok().build();
    }



//    public String hashPassword(String password){
//
//        return  passwordEncoder.encode(password);
//    }
}
