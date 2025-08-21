package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.mova.users.dto.UserDto;
import com.mova.users.model.User;
import com.mova.users.model.UserProfile;
import com.mova.users.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {
    private static final Logger log = LoggerFactory.getLogger(UserProfileService.class);
    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    public UserProfile getUserProfile(String uid){
        UserDto dto = new FirebaseAuthGetInstance().getInstance(uid);
        if (dto != null){
        UserProfile profile = repository.getUserProfileByEmail(dto.getEmail());
        System.out.println(("profile exists: " + profile));



        }




//        System.out.println(profile.toString());
        return new UserProfile();
    }
}
