package com.clamer.utility.jwt;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class  JwtAuthenticationRequest implements Serializable {

    /**********************************************************************
     *
     * JWT 인증 과정에서 사용자 입력 정보를 담을 리퀘스트 객체
     * 로그인 폼에 입력한 유저 네임, 비밀번호
     *
     **********************************************************************/

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
