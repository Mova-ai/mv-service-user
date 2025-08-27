// service/UserService.java
package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.mova.users.dto.UserProfileDTO;
import com.mova.users.model.Role;
import com.mova.users.model.User;
import com.mova.users.model.UserPreferences;
import com.mova.users.model.UserProfile;
import com.mova.users.repository.UserProfileRepository;
import com.mova.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestBody;


import java.util.Optional;


@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repoUser;
    private final UserProfileRepository repoProfile;
    private UserPreferences userPreferences;
    public UserService(UserRepository repo, UserProfileRepository repoProfile) {
        this.repoUser = repo;
        this.repoProfile = repoProfile;
    }

    @Transactional
    public User getUserOrCreate(String uid) throws Exception {
        Optional<User> userOpt = repoUser.findById(uid);

        log.debug("Buscando usuario {} ", uid);

        if (userOpt.isEmpty()){
            log.info("Usuario {} no encontrado, procedemos a crearlo", uid);

            try {
                UserRecord userFr = FirebaseAuth.getInstance().getUser(uid);

                User user = new User();
                user.setUid(userFr.getUid());
                user.setEmail(userFr.getEmail());
                user.setIsActive(true);

                Role  rol = new Role(user);
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
                profile.setUser(user);
                User saved = repoUser.save(user);
                log.info("Usuario {} creado correctamente ", uid);
                return saved;


            } catch (Exception e) {
                log.error("Error creando usuario {} ", uid, e);
                throw e;
            }


        } else {
            log.debug("Usuario {} encontrado en la base de datos", uid);
            return userOpt.get();
        }
    }

    @Transactional
    public UserProfileDTO getUserSelf(String uid) {
        UserProfile userData = repoProfile.findById(uid)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserProfileDTO(
                userData.getUid(),
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPhone(),
                userData.getAvatarUrl(),
                userData.getBirthday(),
                userData.getBio()
        );
    }

    @Transactional
    public UserProfileDTO updateUserSelf(String auth,UserProfileDTO userDTO){
        UserProfile userData = repoProfile.findById(auth).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado"));

        if (userDTO.getFirstName() != null ) userData.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null ) userData.setLastName(userDTO.getLastName());
        if (userDTO.getPhone() != null ) userData.setPhone(userDTO.getPhone());
        if (userDTO.getAvatarUrl() != null ) userData.setAvatarUrl(userDTO.getAvatarUrl());
        if (userDTO.getBirthday() != null ) userData.setBirthday(userDTO.getBirthday());
        if (userDTO.getBio() != null ) userData.setBio(userDTO.getBio());

        repoProfile.save(userData);

        return new UserProfileDTO(userData);
    }

    @Transactional
    public User deactivateUser(String uid) {
        User userData = repoUser.findById(uid)
                .orElseThrow( () -> new RuntimeException("Usuario no encontrado"));

        userData.setIsActive(false);
        userData.setDeletedAt(LocalDateTime.now());

        repoUser.save(userData);

        return userData;

    }

    public User save(User u) { return repoUser.save(u); }

}