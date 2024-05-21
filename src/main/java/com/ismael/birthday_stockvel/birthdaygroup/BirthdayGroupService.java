package com.ismael.birthday_stockvel.birthdaygroup;

import com.ismael.birthday_stockvel.dto.BirthdayGroupDTO;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.groupmember.GroupMemberRepository;
import com.ismael.birthday_stockvel.mapper.BirthdayGroupMapper;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import com.ismael.birthday_stockvel.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BirthdayGroupService {

    private final BirthdayGroupRepository birthdayGroupRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BirthdayGroupService.class);

    public BirthdayGroup createBirthdayGroup(
            String groupName,
            double contributionAmount
     )
    {
        try {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder
                    .getContext()
                    .getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userRepository.findByUserName(currentUsername);

            // Check if the user already has a group
            if (currentUser.getOwnedGroup() != null) {
                throw new IllegalStateException("User already has a birthday group");
            }

            // Create the birthday group
            BirthdayGroup birthdayGroup = new BirthdayGroup();
            birthdayGroup.setGroupName(groupName);
            birthdayGroup.setGroupOwner(currentUser);
            birthdayGroup.setCreationDate(new Date());
            birthdayGroup.setContributionAmount(contributionAmount); // Set contribution amount

            // Save the birthday group
            birthdayGroup = birthdayGroupRepository.save(birthdayGroup);

            // Add the current user as a member of the group
            GroupMember groupMember = new GroupMember();
            groupMember.setUser(currentUser);
            groupMember.setGroup(birthdayGroup);
            groupMember.setJoinDate(new Date());
            groupMemberRepository.save(groupMember);

            // Return the saved birthday group
            return birthdayGroup;
        } catch (Exception e) {
            logger.error("Error occurred while creating birthday group: {}", e.getMessage(), e);
            throw e; // Rethrow the exception to propagate it
        }
    }

    public List<BirthdayGroupDTO> listAllGroups() {
        List<BirthdayGroup> groups = birthdayGroupRepository.findAll();
        return BirthdayGroupMapper.toDTOs(groups);
    }

    public List<BirthdayGroupDTO> getJoinedGroups(Long userId) {
        List<BirthdayGroup> joinedGroups = birthdayGroupRepository.findByMembers_User_UserId(userId);
        return BirthdayGroupMapper.toDTOs(joinedGroups);
    }

    public void removeMemberByOwner(Authentication authentication, Long memberId) {
        // Get the currently authenticated user
        User owner = (User) authentication.getPrincipal();

        // Retrieve the member to be removed
        Optional<GroupMember> optionalMember = groupMemberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            throw new IllegalArgumentException("Member not found with ID: " + memberId);
        }
        GroupMember member = optionalMember.get();

        // Check if the authenticated user is the owner of the group
        if (!member.getGroup().getGroupOwner().equals(owner)) {
            throw new AccessDeniedException("Only the group owner can remove members.");
        }

        // Proceed with member removal
        groupMemberRepository.delete(member);
    }



}
