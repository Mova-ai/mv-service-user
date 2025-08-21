// service/UserService.java
package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.mova.users.dto.UserDto;
import com.mova.users.model.Role;
import com.mova.users.model.User;
import com.mova.users.model.UserPreferences;
import com.mova.users.model.UserProfile;
import com.mova.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository repo;
    private UserPreferences userPreferences;
    public UserService(UserRepository repo) { this.repo = repo; }

    @Transactional
    public User getOrProvision(String uid) throws Exception {
        try {
            Optional<User> user = repo.findById(uid);
            if (user == null) {
                UserRecord fr = FirebaseAuth.getInstance().getUser(uid);

                User u = new User();
                u.setUid(fr.getUid());
                u.setEmail(fr.getEmail());
                u.setIsActive(true);

                Role rol = new Role();
                u.setRole(rol);

                UserPreferences preferences = new UserPreferences();
                preferences.setUser(u);
                u.setPreferences(preferences);

                UserProfile profile = new UserProfile();
                profile.setEmail(u.getEmail());
                profile.setId(u.getUid());
                profile.setUser(u);
                u.setProfile(profile);

                repo.save(u);

            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }


        return repo.findById(uid).orElseGet(() -> {
            try {
                UserRecord fr = FirebaseAuth.getInstance().getUser(uid);
                Optional<User> i = repo.findById(uid);
                System.out.println("Esto es i:\n\t" + i);
                User u = new User();
                u.setUid(fr.getUid());
                u.setEmail(fr.getEmail());
                u.setIsActive(true);

                Role role = new Role(u);
                u.setRole(role);

                UserPreferences prefs = new UserPreferences();
                prefs.setUser(u);
                u.setPreferences(prefs);

                UserProfile profile = new UserProfile();
                u.setProfile(profile);
                if (fr.getDisplayName() != null ) profile.setFirstName(fr.getDisplayName());
                profile.setUser(u);

              
                return repo.save(u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Transactional
    public User getUserOrCreate(String uid) throws Exception {
        Optional<User> userRepo = repo.findById(uid);
        if (userRepo.isEmpty()){
            System.out.println("El usuario no existe. Lo crearemos acá");
            UserRecord userFr = FirebaseAuth.getInstance().getUser(uid);
            User user = new User();
            user.setUid(userFr.getUid());
            user.setEmail(userFr.getEmail());
            user.setIsActive(true);

            Role  rol = new Role();
            user.setRole(rol);

            UserPreferences preferences = new UserPreferences();
            user.setPreferences(preferences);
            preferences.setUser(user);

            UserProfile profile = new UserProfile();
            profile.setEmail(userFr.getEmail());

            if (userFr.getDisplayName() != null) profile.setFirstName(userFr.getDisplayName());
            if (userFr.getPhoneNumber() != null) profile.setPhone(userFr.getPhoneNumber());
            if (userFr.getPhotoUrl() != null) profile.setAvatarUrl(userFr.getPhotoUrl());

            user.setProfile(profile);

            return user;
        } else {
            return userRepo.get();
        }
    }


    public User save(User u) { return repo.save(u); }

}