package com.clamer.domain.repository;

import com.clamer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


/**
 * Created by stephan on 20.03.16.
 */

// @RepositoryRestResource : JPA 이용한 REST API 기본 구조 생성 어노테이션
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

//    REST 방식 호출시 GET 메소드의 경우 URL에 파라미터로 값을 넘기기 때문에 @Param 어노테이션 필수
    User findByUsername(@Param("username") String username);
    List<User> findByUsernameIgnoreCaseContaining(@Param("username") String username);
}
