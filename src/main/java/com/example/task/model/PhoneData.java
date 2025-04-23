package com.example.task.model;

import com.example.task.validation.PhoneNumber;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone_data")
@Data
public class PhoneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @PhoneNumber
    private String phone;

    @Column(nullable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
