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
//    ID (PK), 생성 날짜, 마지막 업데이트 날짜

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = this.updatedAt = new Date();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = new Date();
    }
}
