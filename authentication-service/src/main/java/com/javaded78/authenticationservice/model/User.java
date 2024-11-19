package com.javaded78.authenticationservice.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_user")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "c_email", unique = true, nullable = false)
    private String email;

    @Column(name = "c_password", nullable = false)
    private String password;

    @Column(name = "c_authority")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "t_user_authority", joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(value = EnumType.STRING)
    private Set<Role> authorities;

    @Column(name = "c_is_account_non_expired", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "c_is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "c_is_credentials_non_expired", nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(name = "c_is_enabled", nullable = false)
    private boolean isEnabled;
}
