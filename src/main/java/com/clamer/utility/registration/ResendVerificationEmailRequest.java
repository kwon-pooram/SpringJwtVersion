package com.clamer.utility.registration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by sungman.you on 2017. 4. 22..
 */
@Getter
@Setter
public class ResendVerificationEmailRequest implements Serializable{

    /**********************************************************************
     *
     * 인증 메일 재전송 요청시 사용자가 폼에 입력한 정보를 담을 리퀘스트 객체
     *
     **********************************************************************/

    private String resendVerificationEmailFormEmail;
    private String resendVerificationEmailFormUsername;

    public ResendVerificationEmailRequest() {
        super();
    }

    public ResendVerificationEmailRequest(String resendVerificationEmailFormEmail, String resendVerificationEmailFormUsername) {
        this.resendVerificationEmailFormEmail = resendVerificationEmailFormEmail;
        this.resendVerificationEmailFormUsername = resendVerificationEmailFormUsername;
    }
}
