package com.clamer.controller;

import com.clamer.utility.jwt.JwtTokenUtility;
import com.clamer.utility.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestController {


    private final JwtTokenUtility jwtTokenUtility;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserRestController(JwtTokenUtility jwtTokenUtility, UserDetailsService userDetailsService) {
        this.jwtTokenUtility = jwtTokenUtility;
        this.userDetailsService = userDetailsService;
    }


//    리퀘스트에서 토큰 정보를 가져와서 JWT 유저 객체 반환
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String tokenHeader = "Authorization";
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtility.getUsernameFromToken(token);
        return (JwtUser) userDetailsService.loadUserByUsername(username);
    }

}
