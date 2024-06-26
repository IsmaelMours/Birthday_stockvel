package com.ismael.birthday_stockvel.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroup;
import com.ismael.birthday_stockvel.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 100)
    private String userName;

    @ManyToOne(fetch=FetchType.EAGER)
    private Role roles;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 250)
    private String password;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 20)
    private String mobile;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToOne(mappedBy = "groupOwner")
    private BirthdayGroup ownedGroup;

    private boolean accountLocked;

    private boolean enabled;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[]  profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<BirthdayGroup> joinedGroups;
    //  Define the owning side of the relationship

    public int getBirthMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        return calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is zero-based
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roles.getName().name()));
        System.out.println(roles.getName().name());
        return authorities;
    }


    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getName() {
        return email;
    }

    public String fullName(){

        return  firstName +" " + lastName;
    }
}
