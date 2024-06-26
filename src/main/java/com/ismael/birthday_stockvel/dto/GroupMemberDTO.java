package com.ismael.birthday_stockvel.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GroupMemberDTO {
    private Long groupId;
    private String groupName;
    private String groupOwnerName;
    private List<UserDTO> members;
    private Date joinDate;

}
