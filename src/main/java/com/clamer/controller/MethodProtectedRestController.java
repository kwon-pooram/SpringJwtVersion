package com.clamer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class MethodProtectedRestController {

    @RequestMapping(method = RequestMethod.GET)
//    사용자 권한에 따라서 메소드 단위로 허용 범위 설정
//    실제 DB에 저장할때는 'ROLE_' 앞에 붙여서 저장
//    ex) ROLE_ADMIN, ROLE_USER
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProtectedGreeting() {
        return ResponseEntity.ok("어드민 계정만 호출 가능한 메소드");
    }

}