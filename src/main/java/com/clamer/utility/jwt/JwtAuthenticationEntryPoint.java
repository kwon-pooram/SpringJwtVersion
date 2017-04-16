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


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        미인증 사용자가 제한된 REST 리소스 호출시 에러 반환
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "해당 리소스는 인증된 사용자만 호출 할 수 있습니다.");
    }
}