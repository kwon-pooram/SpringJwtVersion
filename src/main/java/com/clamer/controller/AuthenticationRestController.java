package com.clamer.controller;

import com.clamer.utility.jwt.JwtAuthenticationRequest;
import com.clamer.utility.jwt.JwtTokenUtility;
import com.clamer.utility.jwt.JwtUser;
import com.clamer.utility.jwt.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationRestController {

//    인증 관련 컨트롤러

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtility jwtTokenUtility;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtility jwtTokenUtility, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtility = jwtTokenUtility;
        this.userDetailsService = userDetailsService;
    }



    /**
     * 토큰 생성
     * */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) throws AuthenticationException {

//        인증 과정 수행
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

//        JWT 생성을 위해 유저 정보 다시 불러오기
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

//        JWT 생성
        final String token = jwtTokenUtility.generateToken(userDetails, device);

//        생성된 JWT 반환
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }


    /**
     * 토큰 재생성
     * */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String tokenHeader = "Authorization";
//        리퀘스트 헤더에서 토큰 가져오기
        String token = request.getHeader(tokenHeader);

//        토큰으로 사용자 이름 가져오기
        String username = jwtTokenUtility.getUsernameFromToken(token);

//        인증 과정을 위한 JWT 유저 객체 생성
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

//        토큰 재생성 가능 여부 판단
        if (jwtTokenUtility.canTokenBeRefreshed(token, user.getPasswordUpdatedAt())) {
            String refreshedToken = jwtTokenUtility.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
