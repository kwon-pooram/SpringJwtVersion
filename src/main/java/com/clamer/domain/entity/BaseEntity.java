package com.clamer.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sungman.you on 2017. 4. 16..
 */

@Data
@MappedSuperclass
class BaseEntity implements Serializable{

//    엔티티 공용 요소

//    ID (Primary key)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    생성 시간
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

//    마지막 업데이트 시간
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

//    DB 저장 전 수행 메소드
    @PrePersist
    void onCreate() {
        this.createdAt = this.updatedAt = new Date();
    }

//    DB 업데이트 전 수행 메소드
    @PreUpdate
    void onUpdate() {
        this.updatedAt = new Date();
    }
}
