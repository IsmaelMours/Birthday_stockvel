package com.ismael.birthday_stockvel.mapper;

import com.ismael.birthday_stockvel.dto.GroupJoinRequestDTO;
import com.ismael.birthday_stockvel.joinrequest.GroupJoinRequest;
import org.springframework.stereotype.Service;

@Service
public class GroupJoinRequestMapper {
    public GroupJoinRequestDTO toDTO(GroupJoinRequest joinRequest) {
        GroupJoinRequestDTO dto = new GroupJoinRequestDTO();
        dto.setRequestId(joinRequest.getId());
        dto.setUserName(joinRequest.getUser().fullName());
        dto.setGroupId(joinRequest.getGroup().getGroupId());
        dto.setGroupName(joinRequest.getGroup().getGroupName());
        dto.setRequestDate(joinRequest.getRequestDate());
        dto.setApprovalStatus(joinRequest.getApprovalStatus().name());
        return dto;
    }
}
