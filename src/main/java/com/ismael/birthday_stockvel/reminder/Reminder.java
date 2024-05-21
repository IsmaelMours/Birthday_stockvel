package com.ismael.birthday_stockvel.reminder;


import com.ismael.birthday_stockvel.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reminders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reminder_id")
    private Long reminderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reminder_type")
    private String reminderType;

    @Column(name = "reminder_date")
    private Date reminderDate;

    @Column(name = "reminder_message")
    private String reminderMessage;


}
