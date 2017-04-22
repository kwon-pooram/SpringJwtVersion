package com.clamer.domain.entity;

public enum AuthorityType {
    // 권한 종류 정의

    ROLE_USER,  // 일반 사용자 권한
    ROLE_ADMIN, // 관리자 권한
    ROLE_API_ALLOWED // REST API 직접 호출 가능 권한
}