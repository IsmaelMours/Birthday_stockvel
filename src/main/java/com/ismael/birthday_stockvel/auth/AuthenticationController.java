package com.ismael.birthday_stockvel.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ismael.birthday_stockvel.dto.AuthenticationRequest;
import com.ismael.birthday_stockvel.dto.SignupRequest;
import com.ismael.birthday_stockvel.dto.UserDetailsDTO;
import com.ismael.birthday_stockvel.response.AuthenticationResponse;
import com.ismael.birthday_stockvel.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService service;
    private final ObjectMapper objectMapper;
    private final AuthenticationService authservice;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest request) {
        service.registerUser(request);
        return ResponseEntity.ok("User registered successfully!");
    }



}





