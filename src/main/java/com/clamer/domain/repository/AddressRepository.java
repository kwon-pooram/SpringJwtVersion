package com.clamer.domain.repository;

import com.clamer.domain.entity.Address;
import com.clamer.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

/**
 * Created by sungman.you on 2017. 4. 5..
 */
@RepositoryRestResource
public interface AddressRepository extends JpaRepository<Address, Long> {

    Collection<Address> findByUser(User user);
}
