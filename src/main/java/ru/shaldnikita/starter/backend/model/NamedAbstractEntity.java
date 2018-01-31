package ru.shaldnikita.starter.backend.model;

import javax.persistence.MappedSuperclass;

/**
 * @author n.shaldenkov on 17/01/2018
 */

@MappedSuperclass
public class NamedAbstractEntity extends AbstractEntity {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
