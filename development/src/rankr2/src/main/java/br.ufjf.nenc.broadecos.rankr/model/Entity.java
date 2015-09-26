package br.ufjf.nenc.broadecos.rankr.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public abstract class Entity {

    @Id
    private String id;

    private Date created;

    private Date lastUpdated;

}
