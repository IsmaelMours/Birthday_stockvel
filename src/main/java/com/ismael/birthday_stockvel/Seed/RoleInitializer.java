package com.ismael.birthday_stockvel.Seed;


import com.ismael.birthday_stockvel.role.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {

    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


}