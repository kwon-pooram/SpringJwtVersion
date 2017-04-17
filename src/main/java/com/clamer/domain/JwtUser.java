package com.clamer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * Created by stephan on 20.03.16.
 */
@Getter
public class JwtUser implements UserDetails {

//    User 엔티티를 가져와서 DTO 역할 담당하는 JWT 유저 객체로 반환
//    어노테이션을 통해서 리스펀스로 보낼 정보 수정 용이해짐

    @JsonIgnore
    private final Long id;
    @JsonIgnore
    private final String password;

    private final String username;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final boolean accountNonLocked;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;

    @JsonIgnore
    private final Date passwordUpdatedAt;


    JwtUser(
            Long id,
            String username,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            boolean enabled,
            boolean accountNonLocked,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            Date passwordUpdatedAt
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.passwordUpdatedAt = passwordUpdatedAt;
    }
}
