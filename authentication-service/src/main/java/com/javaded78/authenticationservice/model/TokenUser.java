package com.javaded78.authenticationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "t_user")
public class TokenUser implements UserDetails, BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Token token;

    @Column(name = "c_email", unique = true, nullable = false)
    private String email;

    @Column(name = "c_password", nullable = false)
    private String password;

    @Column(name = "c_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "c_is_account_non_expired", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "c_is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "c_is_credentials_non_expired", nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(name = "c_is_enabled", nullable = false)
    private boolean isEnabled;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }
}
