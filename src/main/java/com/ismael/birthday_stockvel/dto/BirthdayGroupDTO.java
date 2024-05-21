package com.ismael.birthday_stockvel.dto;

import lombok.Data;

@Data
public class BirthdayGroupDTO {

    private Long groupId;
    private String groupName;
    private String groupOwnerName;
    private double contributorAmount;

}
