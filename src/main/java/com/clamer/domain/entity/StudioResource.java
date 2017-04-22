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
public class StudioResource extends BaseEntity implements Serializable {

    @ManyToOne
    private Studio studio;

    private String type;
    private String url;
    private String description;
}
