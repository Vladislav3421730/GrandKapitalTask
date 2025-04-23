package com.example.task.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "password must be not blank")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "name must be not blank")
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    @NotNull(message = "birthday must be not null")
    @PastOrPresent(message = "Birthday must be in the past or present")
    private Date dateOfBirth;

    @Column(nullable = false)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Account account;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<EmailData> emailData;

    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<PhoneData> phoneData;
}
