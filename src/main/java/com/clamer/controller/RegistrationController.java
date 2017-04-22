package com.clamer.controller;

import com.clamer.domain.entity.User;
import com.clamer.domain.entity.VerificationToken;
import com.clamer.service.RegistrationService;
import com.clamer.service.VerificationTokenService;
import com.clamer.utility.eventlistener.OnRegistrationCompleteEvent;
import com.clamer.utility.eventlistener.OnResendVerificationTokenEvent;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sungman.you on 2017. 4. 21..
 */

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    /**********************************************************************
     *
     * 회원 가입 & 이메일 인증 컨트롤러 : /registration/**
     *
     **********************************************************************/

    private final VerificationTokenService verificationTokenService;
    private final RegistrationService registrationService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public RegistrationController(VerificationTokenService verificationTokenService, RegistrationService registrationService, ApplicationEventPublisher eventPublisher) {
        this.verificationTokenService = verificationTokenService;
        this.registrationService = registrationService;
        this.eventPublisher = eventPublisher;
    }


    /**
     * 회원 가입
     **/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request) {

        // 정상적으로 데이터베이스에 사용자가 저장되면 서비스가 유저 객체 반환
        User registeredUser = registrationService.registerNewUser(registrationRequest);

        if (registeredUser != null) {
            // NULL 이 아니면 이메일 발송 시도
            try {
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale(), getAppUrl(request)));
            } catch (Exception exception) {
                // Exception 발생시 익셉션 처리
                throw new GeneralException("인증 메일 발송 과정에서 오류가 발생했습니다. 재발송을 요청하세요.");
            }

            // 회원 가입 성공시 리디렉트 URL 담은 리스펀스 반환
            return ResponseEntity.ok(new GeneralResponse("/emailVerification"));
        } else {
            // 데이터베이스에 정상적으로 저장되지 않았으므로 오류 반환
            return ResponseEntity.badRequest().body(null);
        }
    }


    /**
     * 이메일 인증
     **/
    @RequestMapping(value = "/verifyEmailAddress", method = RequestMethod.POST)
    public ResponseEntity<?> verifyEmailAddress(@RequestBody EmailVerificationRequest emailVerificationRequest) {

        User user = registrationService.verifyEmailAddress(emailVerificationRequest);

        if (user != null) {
            // 유저가 NULL 이 아니면 서비스에서 정상적으로 이메일 인증 완료, 인증된 유저 정보 반환
            // 인증 성공시 리디렉트 URL 담은 리스펀스 반환
            return ResponseEntity.ok(new GeneralResponse("/login"));
        } else {
            // 서비스에서 정상적으로 인증이 완료되지 않았기 때문에 Bad request 반환
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 인증 메일 재발송
     **/
    @RequestMapping(value = "/resendVerificationEmail", method = RequestMethod.POST)
    public ResponseEntity<?> resendVerificationEmail(@RequestBody ResendVerificationEmailRequest resendVerificationRequest,final HttpServletRequest request) {

        // 사용자 입력 정보를 바탕으로 토큰 업데이트
        VerificationToken verificationToken = verificationTokenService.updateToken(resendVerificationRequest);

        // 토큰이 정상적으로 업데이트 되었으면
        if (verificationToken != null) {

            // 새로운 인증 이메일 발송 시도
            try {
                eventPublisher.publishEvent(new OnResendVerificationTokenEvent(verificationToken.getUser().getEmail(),verificationToken.getToken(), request.getLocale(), getAppUrl(request)));
            }catch (Exception exception) {
                throw new GeneralException("인증 메일 재발송 과정에서 오류가 발생했습니다. 잠시 후 다시 시도하세요.");
            }
            // 이메일 발송까지 성공시 리디렉트 URL 담은 리스펀스 반환
            return ResponseEntity.ok(new GeneralResponse("/verifyEmailAddress"));
        } else {
            // 토큰이 정상적으로 업데이트 되지 않았으므로 오류 반환
            return ResponseEntity.badRequest().body(null);
        }
    }


    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
