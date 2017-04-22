package com.clamer.utility.jwt;

import com.clamer.domain.JwtUser;
import com.clamer.domain.entity.Authority;
import com.clamer.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    /**********************************************************************
     *
     * JWT 를 통한 인증 과정이 정상적으로 완료 됬을때 호출됨
     * JWT 유저 객체를 바로 생성하지 않고 팩토리를 호출하여 생성
     *
     **********************************************************************/

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.isEnabled(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.getPasswordUpdatedAt()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityType().name()))
                .collect(Collectors.toList());
    }
}
