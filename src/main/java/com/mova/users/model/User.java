package com.mova.users.model;
import com.mova.users.model.Role;
import com.mova.users.model.Preferences;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Id
    private String uid;

    @Column(nullable = false, unique = true)
    private String email;

    private String displayName;
    private String photo;

    @Embedded
    private Preferences preferences = new Preferences();

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "role_name"))
    private Set<Role> roles;
}
