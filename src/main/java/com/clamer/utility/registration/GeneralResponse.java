package com.clamer.utility.registration;

/**
 * Created by sungman.you on 2017. 4. 22..
 */
public class GeneralResponse {

    /**********************************************************************
     *
     * 회원 가입 & 이메일 인증 관련 프로세스가 정상적으로 완료 됬을때 공통적으로 사용하는 리스펀스 객체
     * 리디렉트 뷰의 주소를 담아서 반환
     *
     **********************************************************************/


    // 리디렉트 뷰 주소
    private final String redirectUrl;

    public GeneralResponse(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
