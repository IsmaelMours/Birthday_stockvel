package com.ismael.birthday_stockvel.birthdaygroup;

import com.ismael.birthday_stockvel.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BirthdayGroupRepository extends JpaRepository<BirthdayGroup, Long> {
    List<BirthdayGroup> findByMembers_User_UserId(Long userId);
    @Query("SELECT bg FROM BirthdayGroup bg JOIN bg.members gm WHERE gm.user.userId = :userId")
    List<BirthdayGroup> findByMembersUserId(@Param("userId") Long userId);

    List<BirthdayGroup> findByGroupOwner(User user);
}
