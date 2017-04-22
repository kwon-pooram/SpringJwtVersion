package com.clamer.utility.jwt;

import java.io.Serializable;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtAuthenticationResponse implements Serializable {

    /**********************************************************************
     *
     * JWT 인증 과정 성공적으로 완료 됬을떄 반환되는 리스펀스 객체
     * 클라이언트에서 LocalStorage 에 저장 할 토큰 가지고 있음
     *
     **********************************************************************/

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
