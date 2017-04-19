package com.clamer.controller;

import com.clamer.domain.JwtUser;
import com.clamer.domain.entity.User;
import com.clamer.domain.repository.UserRepository;
import com.clamer.service.JwtUserDetailsServiceImpl;
import com.clamer.service.JwtService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class PublicRestController {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final JwtService jwtService;
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public PublicRestController(JwtService jwtService, JwtUserDetailsServiceImpl jwtUserDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "ajaxReactiveSearch", method = RequestMethod.POST)
    public List<User> ajaxReactiveSearch(@RequestParam String inputValue) {

        logger.info("[AJAX REACTIVE SEARCH POST REQUEST] : " + inputValue);

        return userRepository.findByUsernameIgnoreCaseContaining(inputValue);
    }

    // 전체 회원 이름 목록 반환
    @RequestMapping(value = "getAllUsername", method = RequestMethod.GET)
    public List<String> getAllUsername() {

        List<User> users = userRepository.findAll();
        List<String> usernames = new ArrayList<>();
        for (User user : users) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }


}
