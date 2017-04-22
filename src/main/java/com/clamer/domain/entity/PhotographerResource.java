package com.clamer.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by sungman.you on 2017. 4. 5..
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class PhotographerResource extends BaseEntity implements Serializable {


    @ManyToOne
    private Photographer photographer;

//    사진 or 영상 타입
    private String type;
//    리소스 주소
    private String url;
//    타이틀
    private String title;
//    설명
    private String description;
}
