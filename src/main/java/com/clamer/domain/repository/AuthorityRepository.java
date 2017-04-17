package com.clamer.domain.repository;

import com.clamer.domain.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by sungman.you on 2017. 4. 16..
 */
@RepositoryRestResource
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
