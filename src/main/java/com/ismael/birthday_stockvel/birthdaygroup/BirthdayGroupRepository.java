package com.ismael.birthday_stockvel.birthdaygroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BirthdayGroupRepository extends JpaRepository<BirthdayGroup, Long> {
    List<BirthdayGroup> findByMembers_User_UserId(Long userId);
}
