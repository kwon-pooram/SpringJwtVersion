package com.clamer.controller;

import com.clamer.domain.entity.User;
import com.clamer.domain.repository.UserRepository;
import com.clamer.service.JwtService;
import com.clamer.service.JwtUserDetailsServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicRestController {

    /**********************************************************************
     *
     * 인증 불필요한 공개 메소드 컨트롤러 : /public/**
     *
     **********************************************************************/

    private final Log logger = LogFactory.getLog(this.getClass());

    private final JwtService jwtService;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final UserRepository userRepository;

    public PublicRestController(JwtService jwtService, JwtUserDetailsServiceImpl jwtUserDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userRepository = userRepository;
    }

    // 전체 회원 이름 목록 반환
    @RequestMapping(value = "/getAllUsername", method = RequestMethod.GET)
    public List<String> getAllUsername() {

        List<User> users = userRepository.findAll();
        List<String> usernameList = new ArrayList<>();
        for (User user : users) {
            usernameList.add(user.getUsername());
        }
        return usernameList;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> publicMethod() {
        Map<String, String> messages = new HashMap<>();
        messages.put("message1", "공용 메세지1");
        messages.put("message2", "공용 메세지2");
        return ResponseEntity.ok().body(messages);
    }
}
