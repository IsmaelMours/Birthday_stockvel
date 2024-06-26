package com.ismael.birthday_stockvel.joinrequest;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {


    List<GroupJoinRequest> findByGroup(BirthdayGroup group);
}