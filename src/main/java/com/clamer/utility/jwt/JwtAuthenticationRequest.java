package com.clamer.utility.jwt;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class  JwtAuthenticationRequest implements Serializable {

//    JWT 인증시 사용되는 리퀘스트 객체

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