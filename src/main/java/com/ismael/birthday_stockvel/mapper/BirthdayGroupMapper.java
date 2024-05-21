package com.ismael.birthday_stockvel.mapper;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.dto.BirthdayGroupDTO;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class BirthdayGroupMapper {

    public static BirthdayGroupDTO toDTO(BirthdayGroup group) {
        BirthdayGroupDTO dto = new BirthdayGroupDTO();
        dto.setGroupId(group.getGroupId());
        dto.setGroupName(group.getGroupName());
        dto.setGroupOwnerName(group.getGroupOwner().fullName());
        dto.setContributorAmount(group.getContributionAmount());
        return dto;
    }

    public static List<BirthdayGroupDTO> toDTOs(List<BirthdayGroup> groups) {
        return groups.stream()
                .map(BirthdayGroupMapper::toDTO)
                .collect(Collectors.toList());
    }
}