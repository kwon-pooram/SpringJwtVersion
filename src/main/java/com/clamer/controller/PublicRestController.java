package com.clamer.controller;

import com.clamer.service.JwtUserDetailsServiceImpl;
import com.clamer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicRestController {


    private final JwtService jwtService;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    public PublicRestController(JwtService jwtService, JwtUserDetailsServiceImpl jwtUserDetailsService) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }



}
