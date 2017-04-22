package com.clamer.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by sungman.you on 2017. 4. 5..
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Photographer extends BaseEntity implements Serializable {

    @OneToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "photographer_id")
    private Collection<PhotographerResource> resources;


//    분야
    private String major;
//    활동 지역
    private String area;
//    썸네일 주소
    private String thumbnail;
//    자기 소개
    private String content;
}