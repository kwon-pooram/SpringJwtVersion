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
@Entity
@Data
public class Address extends BaseEntity implements Serializable {

    private String postNumber;
    private String address;
    private String addressDetail;
    private boolean defaultAddress;

    @ManyToOne
    private User user;

}
