package com.clamer.domain.repository;

import com.clamer.domain.entity.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by sungman.you on 2017. 4. 5..
 */
@RepositoryRestResource
public interface StudioRepository extends JpaRepository<Studio, Long> {
}
