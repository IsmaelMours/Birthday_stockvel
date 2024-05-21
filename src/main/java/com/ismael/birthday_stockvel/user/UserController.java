package com.ismael.birthday_stockvel.user;

import com.ismael.birthday_stockvel.dto.UserDetailsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/user-details")
    public UserDetailsDTO getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
            userDetailsDTO.setFullName(user.fullName());
            userDetailsDTO.setProfilePicture(user.getProfilePicture());
            return userDetailsDTO;
        }
        return null; // Or handle the case where user details are not available
    }
}
