package com.ismael.birthday_stockvel.Seed;


import com.ismael.birthday_stockvel.role.ERole;
import com.ismael.birthday_stockvel.role.Role;
import com.ismael.birthday_stockvel.role.RoleRepository;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;




@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;




    @Override
    public void run(String... args) throws Exception {
        initializeRoles();
    }

    public void initializeRoles() {

            // roleService.initializeRoles()
            for (ERole roleName : ERole.values()) {
                if (!roleRepository.existsByName(roleName)) {
                    Role role = new Role();
                    role.setName(roleName);
                    roleRepository.save(role);
                }
            }

    }


}
