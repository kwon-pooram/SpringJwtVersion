package com.clamer.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Transient // 회원 가입시 비밀번호 재확인 (DB 저장 안함)
    private String passwordConfirmation;

    @Column(columnDefinition = "boolean default true") // 계정 잠김 여부 (비밀번호 입력 몇 회 이상 실패시 계정 잠김)
    private boolean accountNonLocked;

    @Column(columnDefinition = "boolean default true") // 계정 활성화 여부 (이메일 인증 여부)
    private boolean enabled;


    @Temporal(TemporalType.TIMESTAMP)
    @NotNull // 비밀번호 마지막 업데이트 날짜
    private Date passwordUpdatedAt;


    @ManyToMany(fetch = FetchType.EAGER) // 사용자 권한 목록
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
    private List<Authority> authorities;
}