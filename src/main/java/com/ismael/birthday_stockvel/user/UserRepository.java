package com.ismael.birthday_stockvel.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);


    User findByEmailOrUserName(String userEmail, String userEmail1);

    User findByUserName(String admin);
}
