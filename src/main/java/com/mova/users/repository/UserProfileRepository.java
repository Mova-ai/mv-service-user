package com.mova.users.repository;

import com.mova.users.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {
    boolean existsByEmail(String email);

    UserProfile getUserProfileByEmail(String email);
}
