package com.clamer.domain.repository;

import com.clamer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Created by stephan on 20.03.16.
 */

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);
}
