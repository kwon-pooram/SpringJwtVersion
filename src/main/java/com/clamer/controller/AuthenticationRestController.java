package com.clamer.controller;

import com.clamer.domain.JwtUser;
import com.clamer.service.JwtService;
import com.clamer.service.JwtUserDetailsServiceImpl;
import com.clamer.utility.exception.GeneralException;
import com.clamer.utility.jwt.JwtAuthenticationRequest;
import com.clamer.utility.jwt.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationRestController {

    /**********************************************************************
     *
     * 인증 및 JWT 관련 메소드 컨트롤러 : /auth/**
     *
     **********************************************************************/

    private final JwtService jwtService;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    public AuthenticationRestController(JwtService jwtService, JwtUserDetailsServiceImpl jwtUserDetailsService) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    /**
     * 토큰 생성
     **/
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {

//        JWT 생성을 위해 유저 정보 다시 불러오기
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        // 이메일 인증되지 않은 사용자는 로그인 불가
        if (!userDetails.isEnabled()) {
            throw new GeneralException("이메일 인증 후 로그인 가능합니다.");
        }

//        JWT 생성
        final String token = jwtService.generateToken(userDetails, device);

//        생성된 JWT 반환
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }


    /**
     * 토큰 재생성
     **/
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = "Authorization";
//        리퀘스트 헤더에서 토큰 가져오기
        String token = request.getHeader(tokenHeader);

//        토큰으로 사용자 이름 가져오기
        String username = jwtService.getUsernameFromToken(token);

//        인증 과정을 위한 JWT 유저 객체 생성
        JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);

//        토큰 재생성 가능 여부 판단
        if (jwtService.canTokenBeRefreshed(token, user.getPasswordUpdatedAt())) {
            String refreshedToken = jwtService.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 리퀘스트에서 토큰 정보를 가져와서 JWT 유저 객체로 반환
     * 뷰로 넘어가는 객체
     **/
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String tokenHeader = "Authorization";
        String token = request.getHeader(tokenHeader);
        String username = jwtService.getUsernameFromToken(token);
        return (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
    }

}
