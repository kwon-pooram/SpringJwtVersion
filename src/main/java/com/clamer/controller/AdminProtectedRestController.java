package com.clamer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminProtectedRestController {

    /**********************************************************************
     *
     * ROLE_ADMIN 권한 계정 전용 메소드 컨트롤러 : /admin/**
     *
     * 사용자 권한에 따라서 메소드 단위로 허용 범위 설정
     * 실제 DB에 저장할때는 'ROLE_' 앞에 붙여서 저장
     * ex) ROLE_ADMIN, ROLE_USER
     *
     **********************************************************************/

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ResponseEntity<?> getProtectedGreeting() {

        Map<String,String> messages = new HashMap<>();
        messages.put("message1", "어드민 전용 메세지1");
        messages.put("message2", "어드민 전용 메세지2");
        return ResponseEntity.ok().body(messages);
    }

}