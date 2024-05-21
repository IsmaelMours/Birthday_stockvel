package com.ismael.birthday_stockvel.dto;

import lombok.Data;

@Data
public class CreateBirthdayGroupRequest {
    private String groupName;
    private double contributionAmount;
}
