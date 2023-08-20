package com.example.FinalProject.controller;

import com.example.FinalProject.model.AuthRequest;
import com.example.FinalProject.model.MyUser;
import com.example.FinalProject.repository.MyUserRepository;
import com.example.FinalProject.request.SignupRequest;
import com.example.FinalProject.response.MessageResponse;
import com.example.FinalProject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserRepository myUserRepository;
    @Autowired
    private PasswordEncoder encoder;


    @GetMapping
    public String welcome() {
        return "Welcome";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signUpRequest) {
        if (myUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        MyUser user = new MyUser(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));


        myUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }




}
