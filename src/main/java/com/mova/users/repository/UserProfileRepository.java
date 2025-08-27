package com.mova.users.repository;

import com.mova.users.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    boolean existsByEmail(String email);
    UserProfile getUserProfileByEmail(String email);
}
