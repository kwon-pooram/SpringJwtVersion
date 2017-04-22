package com.clamer.domain.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * Created by sungman.you on 2017. 4. 5..
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Message extends BaseEntity implements Serializable {

    @OneToOne
    private User sender;

    @OneToOne
    private User receiver;

    private String title;
    private String content;
    private boolean checked;
}
