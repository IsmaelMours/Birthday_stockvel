package com.ismael.birthday_stockvel.security;

import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User userExist = repository.findByEmailOrUserName(userEmail,userEmail);
        if (userExist == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return (UserDetails) userExist;
    }
}
