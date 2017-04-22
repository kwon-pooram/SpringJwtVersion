package com.clamer.utility.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    /**********************************************************************
     *
     * 클라이언트와 서버간의 통신에서 미인증 사용자의 요청 처리하는 핸들러
     * @see com.clamer.config.WebSecurityConfig 클래스에서 핸들러 등록
     *
     **********************************************************************/

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        미인증 사용자가 제한된 REST 리소스 호출시 에러 반환

//        에러를 표시할 특정 뷰가 없으므로 에러만 반환
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "[REQUEST DENIED. UNAUTHORIZED USER]");


          // 에러 뷰로 리디렉트 하는 경우
//        response.addHeader("error","헤더에 추가 정보 입력 필요한 경우");
//        response.sendRedirect("/에러 뷰 주소");

    }
}