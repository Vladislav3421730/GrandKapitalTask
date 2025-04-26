package com.example.task.model;

import com.example.task.validation.PhoneNumber;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "phone_data")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PhoneData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_data_id_seq")
    @SequenceGenerator(name = "phone_data_id_seq", sequenceName = "phone_data_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @PhoneNumber
    private String phone;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PhoneData(String phone, User user) {
        this.phone = phone;
        this.user = user;
    }
}
