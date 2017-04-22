package com.clamer.utility.eventlistener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by sungman.you on 2017. 4. 9..
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OnResendVerificationTokenEvent extends ApplicationEvent {

    /**********************************************************************
     *
     * 인증 이메일 재전송 요청시 발생 이벤트
     *
     **********************************************************************/

    private String email;

    // 이벤트로 넘어오는 토큰은 이미 데이터베이스 상에서 업데이트가 된 상태로 넘어옴
    private String token;
    private Locale locale;
    private String appURL;

    public OnResendVerificationTokenEvent(String email,String token, Locale locale, String appURL) {
        super(email);

        this.email = email;
        this.token = token;
        this.appURL = appURL;
        this.locale = locale;
    }
}
