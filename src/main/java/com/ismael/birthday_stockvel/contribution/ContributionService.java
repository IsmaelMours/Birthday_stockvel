package com.ismael.birthday_stockvel.contribution;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroupRepository;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.groupmember.GroupMemberRepository;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public void contributeToBirthdayGroup(Authentication authentication, Long groupId, double contributionAmount)
    {
        // Get the currently authenticated user
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }
        String currentUsername = authentication.getName();
        User contributor = userRepository.findByUserName(currentUsername);

        // Retrieve the BirthdayGroup object using the provided ID
        Optional<BirthdayGroup> optionalBirthdayGroup = birthdayGroupRepository.findById(groupId);
        if (!optionalBirthdayGroup.isPresent()) {
            throw new IllegalArgumentException("Birthday group not found with ID: " + groupId);
        }
        BirthdayGroup group = optionalBirthdayGroup.get();

        // Check if the user is a member of the group
        if (!groupMemberRepository.existsByUserAndGroup(contributor, group)) {
            throw new IllegalArgumentException("User is not a member of the group");
        }

        // Create the contribution
        Contribution contribution = new Contribution();
        contribution.setContributor(contributor);
        contribution.setGroup(group);
        contribution.setContributionAmount(contributionAmount);
        contribution.setContributionDate(new Date());

        // Save the contribution
        contributionRepository.save(contribution);
    }

}
