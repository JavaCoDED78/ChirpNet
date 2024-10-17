package com.javaded78.authenticationservice.security.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class TokenUser extends User {

    private final Token token;

    private TokenUser(Builder builder) {
        super(builder.username,
                builder.password,
                builder.enabled,
                builder.accountNonExpired,
                builder.credentialsNonExpired,
                builder.accountNonLocked,
                builder.authorities);
        this.token = builder.token;
    }

    public static class Builder {

        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private Token token;
        private boolean enabled;
        private boolean accountNonExpired;
        private boolean credentialsNonExpired;
        private boolean accountNonLocked;

        public Builder token(Token token) {
            this.token = token;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder accountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder credentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }

        public Builder accountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public TokenUser build() {
            return new TokenUser(this);
        }
    }
}

