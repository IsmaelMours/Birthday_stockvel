package com.ismael.birthday_stockvel.auth;


import com.ismael.birthday_stockvel.dto.AuthenticationRequest;
import com.ismael.birthday_stockvel.dto.SignupRequest;
import com.ismael.birthday_stockvel.response.AuthenticationResponse;
import com.ismael.birthday_stockvel.role.ERole;
import com.ismael.birthday_stockvel.role.Role;
import com.ismael.birthday_stockvel.role.RoleRepository;
import com.ismael.birthday_stockvel.security.JwtService;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getUserId())
                .email(user.getEmail())
                .roles(user.getRoles().getName().toString())
                .build();
    }

    public void registerUser(SignupRequest request) {
        User existingUser = userRepository.findByEmailOrUserName(request.getEmail(), request.getUserName());
        if (existingUser != null) {
            throw new IllegalArgumentException("Email or username already exists");
        }

        // Create a new user object
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMobile(request.getMobile());
        user.setCreatedDate(LocalDateTime.now());
        user.setBirthDate(request.getBirthDate());
        user.setEnabled(true);

        // Set other properties as needed
        Optional<Role> optionalRole = roleRepository.findByName(ERole.ROLE_USER);
        Role userRole = optionalRole.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(ERole.ROLE_USER);
            return roleRepository.save(newRole);
        });

        user.setRoles(userRole);


        // Save the user to the database
        userRepository.save(user);
    }




}
