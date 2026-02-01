package ru.sicampus.bootcamp2026.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    private String patronymic;

    private String position;

    @Column(unique = true)
    private String email;

    @Column(name = "hash_password")
    private String hashPassword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Meeting> meetingOwner;

    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL)
    private List<Invitation> invitation;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<UsersMeeting> myMeeting;

}
