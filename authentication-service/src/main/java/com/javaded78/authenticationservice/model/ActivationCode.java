package com.javaded78.authenticationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_activation_code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ActivationCode implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "c_user_id")
    private User user;

    @Column(name = "c_code")
    private String code;

    @Column(name = "c_expiration_time")
    private LocalDateTime expirationTime;

}
