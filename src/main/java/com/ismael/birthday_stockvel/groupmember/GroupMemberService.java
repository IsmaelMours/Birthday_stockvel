package com.ismael.birthday_stockvel.groupmember;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroupRepository;
import com.ismael.birthday_stockvel.dto.GroupJoinRequestDTO;
import com.ismael.birthday_stockvel.dto.GroupMemberDTO;
import com.ismael.birthday_stockvel.enum_status.ApprovalStatus;
import com.ismael.birthday_stockvel.joinrequest.GroupJoinRequest;
import com.ismael.birthday_stockvel.joinrequest.GroupJoinRequestRepository;
import com.ismael.birthday_stockvel.mapper.GroupJoinRequestMapper;
import com.ismael.birthday_stockvel.mapper.GroupMemberMapper;
import com.ismael.birthday_stockvel.user.User;
import com.ismael.birthday_stockvel.user.UserRepository;
import com.ismael.birthday_stockvel.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupMemberService {
    private final GroupMemberRepository groupMemberRepository;
    private final BirthdayGroupRepository birthdayGroupRepository;
    private final UserRepository userRepository;
    private final GroupJoinRequestRepository groupJoinRequestRepository;
    private final GroupMemberMapper groupMemberMapper;
    private final GroupJoinRequestMapper groupJoinRequestMapper;

    @Transactional
    public void joinGroup(Authentication authentication, Long groupId) {

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
        BirthdayGroup group = birthdayGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Birthday group not found with ID: " + groupId));

        // Check if the user is already a member of the group
        if (groupMemberRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalStateException("User is already a member of the group");
        }

        // Create a new group join request
        GroupJoinRequest joinRequest = new GroupJoinRequest();
        joinRequest.setUser(user);
        joinRequest.setGroup(group);
        joinRequest.setRequestDate( new Date());
        joinRequest.setApprovalStatus(ApprovalStatus.PENDING); // Set initial status to PENDING

        // Save the join request
        groupJoinRequestRepository.save(joinRequest);
    }



    @Transactional
    public List<GroupMemberDTO> getGroupMembers(Authentication authentication) {
        // Check if the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        // Get the currently authenticated user
        User authenticatedUser = (User) authentication.getPrincipal();

        // Fetch all groups the user is a member of
        List<BirthdayGroup> groups = birthdayGroupRepository.findByMembersUserId(authenticatedUser.getUserId());

        // Fetch and return the group members for these groups as DTOs
        List<GroupMemberDTO> groupMemberDTOs = new ArrayList<>();
        for (BirthdayGroup group : groups) {
            GroupMemberDTO groupMemberDTO = groupMemberMapper.groupMemberToDTO(group, authenticatedUser.getUserId());
            groupMemberDTOs.add(groupMemberDTO);
        }

        return groupMemberDTOs;
    }

    @Transactional
    public void approveJoinRequest(Long requestId) {
        GroupJoinRequest joinRequest = groupJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Join request not found"));

        BirthdayGroup group = joinRequest.getGroup();
        User user = joinRequest.getUser();

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

        // Mark the join request as approved
        joinRequest.setApprovalStatus(ApprovalStatus.APPROVED);
        groupJoinRequestRepository.save(joinRequest);
    }

    @Transactional
    public void rejectJoinRequest(Long requestId) {
        GroupJoinRequest joinRequest = groupJoinRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Join request not found"));

        // Delete the join request
        groupJoinRequestRepository.delete(joinRequest);
    }


    @Transactional
    public List<GroupJoinRequestDTO> getJoinRequestsForOwner(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        // Get the currently authenticated user
        User authenticatedUser = (User) authentication.getPrincipal();

        // Fetch the user from the repository
        User user = userRepository.findById(authenticatedUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Fetch all groups owned by the user
        List<BirthdayGroup> ownedGroups = birthdayGroupRepository.findByGroupOwner(user);

        // Fetch join requests for these groups
        List<GroupJoinRequestDTO> joinRequestDTOs = new ArrayList<>();
        for (BirthdayGroup group : ownedGroups) {
            List<GroupJoinRequest> joinRequests = groupJoinRequestRepository.findByGroup(group);
            for (GroupJoinRequest joinRequest : joinRequests) {
                joinRequestDTOs.add(groupJoinRequestMapper.toDTO(joinRequest));
            }
        }

        return joinRequestDTOs;
    }


}
