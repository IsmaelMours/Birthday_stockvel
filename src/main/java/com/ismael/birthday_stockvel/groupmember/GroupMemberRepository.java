package com.ismael.birthday_stockvel.groupmember;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    // Define custom methods if needed
    List<GroupMember> findByGroup(BirthdayGroup group);

    boolean existsByUserAndGroup(User user, BirthdayGroup group);


    int countByUser(User user);
}

