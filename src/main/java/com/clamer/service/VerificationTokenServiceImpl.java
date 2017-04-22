package com.clamer.service;

import com.clamer.domain.entity.User;
import com.clamer.domain.entity.VerificationToken;
import com.clamer.domain.repository.UserRepository;
import com.clamer.domain.repository.VerificationTokenRepository;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.registration.ResendVerificationEmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by sungman.you on 2017. 4. 8..
 */

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {


    /**********************************************************************
     *
     * 이메일 인증 토큰 관련 서비스
     *
     **********************************************************************/


    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }


    // 데이터베이스에서 토큰 삭제
    @Override
    public void deleteToken(String token) {
        tokenRepository.delete(tokenRepository.findByToken(token));
    }


    // 데이터베이스에 토큰 저장
    @Override
    public void createToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

    }


    // 토큰 정보 업데이트
    @Override
    public VerificationToken updateToken(ResendVerificationEmailRequest resendVerificationEmailRequest) {


        // 사용자가 입력한 이메일 주소와 유저 네임과 일치하는 사용자 검색
        User user = userRepository
                .findByEmailAndUsername(
                        resendVerificationEmailRequest.getResendVerificationEmailFormEmail(),
                        resendVerificationEmailRequest.getResendVerificationEmailFormUsername()
                );

        if (user == null) {
            // 사용자 검색 실패시 예외 처리
            throw new GeneralException("가입하지 않은 사용자 이거나, 잘못된 이메일 또는 사용자 이름입니다. 확인 후 다시 시도해주세요.");
        }


        // 위에서 검색된 사용자로 데이터베이스에 저장된 토큰 정보 검색
        VerificationToken verificationToken = tokenRepository.findByUser(user);


        if (verificationToken == null) {
            // 회원 가입 했는데 토큰이 데이터베이스에 존재 하지 않으면 비정상적으로 회원 가입한 사용자로 간주하고 예외 처리
            throw new GeneralException("정상적으로 가입한 사용자가 아닙니다. 관리자에게 문의하세요.");
        }

        // 토큰 정보가 검색 되면
        else {
            // 새로운 랜덤한 토큰 생성
            String newToken = UUID.randomUUID().toString();
            verificationToken.setToken(newToken);
            tokenRepository.save(verificationToken);


            // 업데이트 된 토큰 반환
            return verificationToken;
        }
    }


    // 데이터베이스에서 토큰 정보 가져오기
    @Override
    public VerificationToken getToken(String token) {
        return tokenRepository.findByToken(token);
    }


}
