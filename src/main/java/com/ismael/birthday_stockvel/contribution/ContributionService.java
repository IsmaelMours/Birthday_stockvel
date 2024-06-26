package com.ismael.birthday_stockvel.contribution;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroupRepository;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.groupmember.GroupMemberRepository;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContributionService {

    private final GroupMemberRepository groupMemberRepository;


    private final UserRepository userRepository;

    private final BirthdayGroupRepository birthdayGroupRepository;


    private final ContributionRepository contributionRepository;

    @Transactional
    public void contributeToBirthdayMember(Authentication authentication, Long groupId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        User contributor = (User) authentication.getPrincipal();
        BirthdayGroup group = birthdayGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Birthday group not found with ID: " + groupId));

        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();

        List<GroupMember> groupMembers = group.getMembers();
        GroupMember birthdayMember = groupMembers.stream()
                .filter(member -> member.getUser().getBirthMonth() == currentMonth)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No member with a birthday this month"));

        Contribution contribution = new Contribution();
        contribution.setContributor(contributor);
        contribution.setGroup(group);
        contribution.setContributionAmount(group.getContributionAmount());
        contribution.setContributionDate(java.sql.Date.valueOf(currentDate));

        contributionRepository.save(contribution);
    }

}
