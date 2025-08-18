package com.mova.users.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
        super();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}


