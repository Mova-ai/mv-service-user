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

    public User() {
    }

    public User(String uid, String email, String displayName, String photo) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.photo = photo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
