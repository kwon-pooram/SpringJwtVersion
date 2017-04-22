package com.clamer.utility.registration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by sungman.you on 2017. 4. 21..
 */
@Getter
@Setter
public class EmailVerificationRequest implements Serializable {

    /**********************************************************************
     *
     * 이메일 인증 요청시 사용자가 폼에 입력한 정보를 담을 리퀘스트 객체
     *
     **********************************************************************/

    private String emailVerificationEmailAddress;
    private String emailVerificationCode;

    public EmailVerificationRequest() {
        super();
    }

    public EmailVerificationRequest(String emailVerificationCode, String emailVerificationEmailAddress) {
        this.emailVerificationCode = emailVerificationCode;
        this.emailVerificationEmailAddress = emailVerificationEmailAddress;
    }
}
