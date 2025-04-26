package com.example.task.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "email_data")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_data_id_seq")
    @SequenceGenerator(name = "email_data_id_seq", sequenceName = "email_data_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @Email(message = "email must contains @")
    @NotNull(message = "email must not be null")
    private String email;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public EmailData(String email, User user) {
        this.email = email;
        this.user = user;
    }
}
