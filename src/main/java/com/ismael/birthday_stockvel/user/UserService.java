package com.ismael.birthday_stockvel.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
