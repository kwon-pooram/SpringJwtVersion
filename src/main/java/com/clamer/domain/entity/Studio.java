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
public class Studio extends BaseEntity implements Serializable {

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "studio_id")
    private Collection<StudioResource> resources;


    private String postNumber;
    private String address;
    private String addressDetail;

    private String contactNumber;

    private String thumbnail;
    private String content;

}
