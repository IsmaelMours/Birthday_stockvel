package com.ismael.birthday_stockvel.groupmember;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroupRepository;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final BirthdayGroupRepository birthdayGroupRepository;

    public void joinGroup(Authentication authentication, Long groupId)
    {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        // Get the currently authenticated user
        User user = (User) authentication.getPrincipal();

        // Check the number of groups the user is already a member of
        int groupCount = groupMemberRepository.countByUser(user);
        if (groupCount >= 3) {
            throw new IllegalStateException("User can only join up to 3 groups");
        }

        // Retrieve the BirthdayGroup object using the provided ID
        Optional<BirthdayGroup> optionalBirthdayGroup = birthdayGroupRepository.findById(groupId);
        if (!optionalBirthdayGroup.isPresent()) {
            throw new IllegalArgumentException("Birthday group not found with ID: " + groupId);
        }
        BirthdayGroup group = optionalBirthdayGroup.get();

        // Check if the user is already a member of the group
        if (groupMemberRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalStateException("User is already a member of the group");
        }

        // Check if the group is full (contains 12 members)
        if (group.getMembers().size() >= 12) {
            throw new IllegalStateException("Group is already full");
        }

        // Validate unique birth months
        Set<Integer> birthMonths = new HashSet<>();
        for (GroupMember member : group.getMembers()) {
            int month = member.getUser().getBirthMonth();
            if (birthMonths.contains(month)) {
                throw new IllegalStateException("Each member must have a unique birth month.");
            }
            birthMonths.add(month);
        }

        // Create a new group member and set the join date
        GroupMember groupMember = new GroupMember();
        groupMember.setUser(user);
        groupMember.setGroup(group);
        groupMember.setJoinDate(new Date());

        // Save the new group member
        groupMemberRepository.save(groupMember);
    }
}
