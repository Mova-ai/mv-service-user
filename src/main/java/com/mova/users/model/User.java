package com.mova.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name="users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String uid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column()
    private Instant updatedAt = Instant.now();

    @Column()
    private LocalDateTime deletedAt = null;

    @Column(nullable = false)
    private Boolean isActive;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName ="id")
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    @JsonManagedReference
    private UserPreferences preferences;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private UserProfile profile;


    public User() {
    }

    public User(String uid,
                String email,
                Instant createdAt,
                Instant updatedAt,
                Role role,
                LocalDateTime deletedAt,
                UserPreferences preferences,
                UserProfile profile

    ) {
        this.uid = uid;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = true;
        this.role = role;
        this.deletedAt = deletedAt;
        this.preferences = preferences;
        if (preferences != null) {
            preferences.setUser(this);
        };


        if (profile != null) {
            profile.setUser(this);
        }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;

    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                ", role=" + role +
                ", preferences=" + preferences +
                ", profile=" + profile +
                '}';

    }
}
