package com.clamer.utility.eventlistener;

import com.clamer.domain.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by sungman.you on 2017. 4. 8..
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    /**********************************************************************
     *
     * 회원 가입시 발생하는 인증 이메일 발송 이벤트
     *
     **********************************************************************/

    // 가입 한 사용자 정보
    private User user;

    // 접속한 클라이언트 언어 정보
    // 클라이언트에 맞춰서 서로 다른 언어로 메일 전송 (예정)
    private Locale locale;

    /**
     * 웹 어플리케이션 주소
     * @see com.clamer.controller.RegistrationController
     **/
    private String appURL;

    public OnRegistrationCompleteEvent(User user, Locale locale, String appURL) {
        super(user);

        this.user = user;
        this.appURL = appURL;
        this.locale = locale;
    }
}
