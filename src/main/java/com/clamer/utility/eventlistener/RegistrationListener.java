package com.clamer.utility.eventlistener;

import com.clamer.domain.entity.User;
import com.clamer.service.MailService;
import com.clamer.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by sungman.you on 2017. 4. 8..
 */

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    /**********************************************************************
     *
     * 회원 가입시 발생하는 인증 이메일 발송 이벤트 발생시 실제로 수행되는 메소드 정의
     *
     **********************************************************************/


    private final MailService mailService;
    private final VerificationTokenService verificationTokenService;

    // 언어 별 메일 전송 기능 추가시 저장되어 있는 내용 가져오는 객체
    // private final MessageSource messageSource;

    @Autowired
    public RegistrationListener(MailService mailService, VerificationTokenService verificationTokenService) {
        this.mailService = mailService;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        // OnRegistrationCompleteEvent 에서 정보 가져옴
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        // 데이터베이스에 토큰 저장
        verificationTokenService.createToken(user, token);


        // 발송 할 메일 관련 정보 정의
        String emailAddress = user.getEmail();
        String emailSubject = "Registration Confirmation";
        String emailMessage = "이 메세지는 이메일 인증 메일 내용입니다. 입력란에 코드를 입력해주세요.";

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(emailAddress);
        email.setFrom("sungman.you@gmail.com");
        email.setSubject(emailSubject);
        email.setText(emailMessage + token);

        mailService.sendEmail(email);
    }
}
