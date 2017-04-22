package com.clamer.service;

import com.clamer.domain.entity.Authority;
import com.clamer.domain.entity.AuthorityType;
import com.clamer.domain.entity.User;
import com.clamer.domain.entity.VerificationToken;
import com.clamer.domain.repository.AuthorityRepository;
import com.clamer.domain.repository.UserRepository;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.registration.EmailVerificationRequest;
import com.clamer.utility.registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sungman.you on 2017. 4. 21..
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    /**********************************************************************
     *
     * 회원 가입 & 이메일 인증 관련 서비스
     *
     **********************************************************************/

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final VerificationTokenService verificationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository,
                                   AuthorityRepository authorityRepository,
                                   VerificationTokenService verificationTokenService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.verificationTokenService = verificationTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User registerNewUser(RegistrationRequest registrationRequest) throws GeneralException {
        User user = new User();
        List<Authority> authorities = new ArrayList<>();

        // 서버 사이드 이메일 중복 검사 (.toLowerCase() : 소문자 치환 후 검사)
        if (userRepository.findByEmail(registrationRequest.getEmail().toLowerCase()) != null) {
            throw new GeneralException("이메일 중복 : " + registrationRequest.getEmail());
        }
        // 서버 사이드 유저네임 중복 검사 (.toLowerCase() : 소문자 치환 후 검사)
        if (userRepository.findByUsername(registrationRequest.getUsername().toLowerCase()) != null) {
            throw new GeneralException("유저네임 중복 : " + registrationRequest.getUsername());
        }
        // 서버 사이드 사용자 입력 비밀번호 검증
        if (!(registrationRequest.getPassword()).equals(registrationRequest.getPasswordConfirmation())) {
            throw new GeneralException("비밀번호 불일치");
        }

        // 리퀘스트 객체 데이터베이스 저장을 위해 유저 객체로 치환
        // 이메일과 유저네임은 소문자 치환
        user.setEmail(registrationRequest.getEmail().toLowerCase());
        user.setUsername(registrationRequest.getUsername().toLowerCase());

        // 비밀번호 인코딩
        user.setPassword(bCryptPasswordEncoder.encode(registrationRequest.getPasswordConfirmation()));

        // 마지막 비밀번호 업데이트 날짜 지정
        user.setPasswordUpdatedAt(new Date());

        // 이메일 미인증 상태 : FALSE
        user.setEnabled(false);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);

        // ROLE_USER 권한 등록
        authorities.add(authorityRepository.findByAuthorityType(AuthorityType.ROLE_USER));
        user.setAuthorities(authorities);

        // 리포지토리 NULL 반환시 데이터베이스에 저장 실패이므로 익셉션 처리
        if (userRepository.save(user) == null) {
            throw new GeneralException("회원 가입 실패 : " + registrationRequest.getUsername());
        }

        // 정상적으로 저장 완료되면 저장한 유저 객체 반환
        return user;
    }


    @Override
    public User verifyEmailAddress(EmailVerificationRequest emailVerificationRequest) throws GeneralException {

        User user = userRepository.findByEmail(emailVerificationRequest.getEmailVerificationEmailAddress());
        VerificationToken verificationToken = verificationTokenService.getToken(emailVerificationRequest.getEmailVerificationCode());

        // 사용자가 입력한 이메일 주소로 유저 정보 찾기
        if (user == null) {
            throw new GeneralException("가입되지 않은 이메일 주소 : " + emailVerificationRequest.getEmailVerificationEmailAddress());
        }

        // 사용자가 입력한 토큰으로 토큰 정보 찾기
        if (verificationToken == null) {
            throw new GeneralException("유효하지 않은 코드 : " + emailVerificationRequest.getEmailVerificationCode());
        }

        // 유저 PK 와 토큰이 가지고 있는 유저의 PK가 같으면
        if ((user.getId()).equals(verificationToken.getUser().getId())) {

            // 토큰 만료 날짜 확인
            // 인증 할 때 날짜가 만료 날짜와 같거나 만료 날짜보다 뒤에 있으면
            if (new Date().compareTo(verificationToken.getExpiryDate()) > -1) {
                // 이미 만료된 코드
                throw new GeneralException("유효 기간이 지난 코드. 인증 이메일 재발송을 요청하세요.");
            }

            try{
                // 이메일 인증 시도
                user.setEnabled(true);
                userRepository.save(user);

                // 인증에 사용한 토큰 DB 에서 삭제
                verificationTokenService.deleteToken(emailVerificationRequest.getEmailVerificationCode());

            } catch(Exception exception) {
                throw new GeneralException("이메일 인증 실패 : " + exception.getMessage());
            }
            return user;
        } else {
            // PK 같지 않으면 정상적으로 접근하지 않은것으로 간주 예외 처리
            throw new GeneralException("이메일 인증 실패 : " + emailVerificationRequest.getEmailVerificationEmailAddress());
        }
    }


}
