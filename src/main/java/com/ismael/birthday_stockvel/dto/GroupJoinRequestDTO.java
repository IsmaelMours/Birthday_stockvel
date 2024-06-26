package com.ismael.birthday_stockvel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupJoinRequestDTO {
    private Long requestId;
    private String userName;
    private Long groupId;
    private String groupName;
    private Date requestDate;
    private String approvalStatus;
}
