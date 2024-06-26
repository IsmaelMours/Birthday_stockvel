package com.ismael.birthday_stockvel.mapper;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.dto.GroupMemberDTO;
import com.ismael.birthday_stockvel.dto.UserDTO;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMemberMapper {

    public GroupMemberDTO groupMemberToDTO(BirthdayGroup group, Long userId) {
        GroupMemberDTO dto = new GroupMemberDTO();

        // Populate group name and owner's name
        dto.setGroupName(group.getGroupName());
        dto.setGroupId(group.getGroupId());
        User groupOwner = group.getGroupOwner();
        if (groupOwner != null) {
            dto.setGroupOwnerName(groupOwner.fullName());
        }

        // Populate members for the group
        List<UserDTO> membersDTO = group.getMembers().stream()
                .map(member -> {
                    UserDTO userDTO = new UserDTO();
                    User user = member.getUser();
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setBirthDate(user.getBirthDate());
                    // Set other user information as needed
                    return userDTO;
                })
                .collect(Collectors.toList());
        dto.setMembers(membersDTO);

        // Set join date
        // This assumes the join date is stored directly in the GroupMember entity
        // You may need to adjust this based on your entity structure
        GroupMember groupMember = group.getMembers().stream().findFirst().orElse(null);
        if (groupMember != null) {
            dto.setJoinDate(groupMember.getJoinDate());
        }

        return dto;
    }



}
