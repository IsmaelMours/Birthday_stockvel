package com.ismael.birthday_stockvel.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    boolean existsByName(ERole name);

    Optional<Role> findByName(ERole eRole);
}
