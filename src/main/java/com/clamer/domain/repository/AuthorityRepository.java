package com.clamer.domain.repository;

import com.clamer.domain.entity.Authority;
import com.clamer.domain.entity.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by sungman.you on 2017. 4. 16..
 */


// @RepositoryRestResource : JPA 이용한 REST API 기본 구조 생성 어노테이션
@RepositoryRestResource
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityType(AuthorityType authorityType);
}
