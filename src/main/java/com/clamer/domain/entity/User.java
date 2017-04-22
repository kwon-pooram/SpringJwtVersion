package com.clamer.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends BaseEntity implements Serializable {

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(columnDefinition = "boolean default true") // 자격 만료 여부 (토큰 만료)
    private boolean credentialsNonExpired;

    @Column(columnDefinition = "boolean default true") // 계정 만료 여부 (사용 미정)
    private boolean accountNonExpired;

    @Column(columnDefinition = "boolean default true") // 계정 잠김 여부 (사용 미정)
    private boolean accountNonLocked;

    @Column(columnDefinition = "boolean default true") // 계정 활성화 여부 (이메일 인증)
    private boolean enabled;


    @Temporal(TemporalType.TIMESTAMP)
    @NotNull // 비밀번호 마지막 업데이트 날짜 (비밀번호 변경시 기존에 발급된 토큰 사용 불가)
    private Date passwordUpdatedAt;


    @ManyToMany(fetch = FetchType.EAGER) // 사용자 권한 목록
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Studio> studios;
}