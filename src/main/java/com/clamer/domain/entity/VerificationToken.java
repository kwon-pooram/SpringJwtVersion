package com.clamer.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sungman.you on 2017. 4. 8..
 */
@Data
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(value = TemporalType.TIMESTAMP)
    // 만료 날짜
    private Date expiryDate;


    @PrePersist
    @PreUpdate
    void onCreateOrUpdate() {
        // 데이터베이스에 토큰 저장하거나 업데이트시 수행되는 메소드
        // 만료 날짜에 지정한 시간 만큼 더함

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);

         c.add(Calendar.DATE, 1);  // 하루
        // c.add(Calendar.MINUTE, 10); 10분
        // c.add(Calendar.SECOND, 30); // 30초

        dt = c.getTime();

        this.expiryDate = dt;
    }
}
