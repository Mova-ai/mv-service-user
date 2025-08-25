package com.mova.users.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name = "USER";

    @OneToOne
    @JoinColumn(name = "name_id", nullable = false, unique = true)
    private User user;


    public Role(User user) {
        this.user = user;
    }

    public Role() {}

    public Long getId() {
        return id;
    }
    public void setId(Long name) {
        this.id = name;

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
                "role='" + id + '\'' +
                '}';
    }
}


