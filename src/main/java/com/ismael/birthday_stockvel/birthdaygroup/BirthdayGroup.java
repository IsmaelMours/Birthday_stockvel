package com.ismael.birthday_stockvel.birthdaygroup;

import com.ismael.birthday_stockvel.contribution.Contribution;
import com.ismael.birthday_stockvel.groupmember.GroupMember;
import com.ismael.birthday_stockvel.message.Message;
import com.ismael.birthday_stockvel.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "birthday_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BirthdayGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    @OneToOne
    @JoinColumn(name = "group_owner_id")
    private User groupOwner;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> members;

    @OneToMany(mappedBy = "group")
    private List<Contribution> contributions;


    @OneToMany(mappedBy = "group")
    private List<Message> messages;

    @Column(name = "contribution_amount")
    private double contributionAmount;

    private Date creationDate;

}
