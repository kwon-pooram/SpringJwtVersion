package com.clamer.utility.eventlistener;

import com.clamer.service.MailService;
import com.clamer.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * Created by sungman.you on 2017. 4. 8..
 */

@Component
public class ResendVerificationListener implements ApplicationListener<OnResendVerificationTokenEvent> {


    /**********************************************************************
     *
     * 인증 이메일 재전송 요청시 발생 이벤트 실제 수행되는 메소드 정의
     *
     **********************************************************************/

    private final MailService mailService;


    @Autowired
    public ResendVerificationListener(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void onApplicationEvent(OnResendVerificationTokenEvent event) {
        this.resendVerificationEmail(event);
    }

    private void resendVerificationEmail(OnResendVerificationTokenEvent event) {
        String emailAddress = event.getEmail();
        String newToken = event.getToken();

        String emailSubject = "Resend verification token";
        String emailMessage = "이 메세지는 이메일 인증 재전송 내용입니다. 입력란에 다음 코드를 입력해주세요.";

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(emailAddress);
        email.setFrom("sungman.you@gmail.com");
        email.setSubject(emailSubject);
        email.setText(emailMessage + "[" + newToken + "]");

        mailService.sendEmail(email);
    }
}
