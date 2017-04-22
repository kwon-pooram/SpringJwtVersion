package com.clamer.utility.registration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by sungman.you on 2017. 4. 21..
 */
@Getter
@Setter
public class RegistrationRequest implements Serializable {

    /**********************************************************************
     *
     * 회원 가입 요청시 사용자가 폼에 입력한 정보를 담을 리퀘스트 객체
     *
     **********************************************************************/

    private String email;
    private String username;
    private String password;
    private String passwordConfirmation;

    public RegistrationRequest() {
        super();
    }

    public RegistrationRequest(String email, String username, String password, String passwordConfirmation) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
