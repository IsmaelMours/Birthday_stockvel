package com.ismael.birthday_stockvel.joinrequest;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.enum_status.ApprovalStatus;
import com.ismael.birthday_stockvel.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private BirthdayGroup group;

    private Date requestDate;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;


}