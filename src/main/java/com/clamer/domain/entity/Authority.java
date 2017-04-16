package com.clamer.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Authority extends BaseEntity implements Serializable {

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;


    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

}