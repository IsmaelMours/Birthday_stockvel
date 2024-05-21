package com.ismael.birthday_stockvel.contribution;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "contributions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contribution_id")
    private Long contributionId;

    @ManyToOne
    @JoinColumn(name = "contributor_id")
    private User contributor;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private BirthdayGroup group;

    @Column(name = "contribution_amount")
    private double contributionAmount;

    @Column(name = "contribution_date")
    private Date contributionDate;


}
