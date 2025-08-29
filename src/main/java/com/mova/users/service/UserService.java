// service/UserService.java
package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
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
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository repoUser;
    private final UserProfileRepository repoProfile;
    private UserPreferences userPreferences;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository repo, UserProfileRepository repoProfile) {
        this.repoUser = repo;
        this.repoProfile = repoProfile;
    }

    @Transactional
    public User getUserOrCreate(String uid) throws Exception {
        log.debug("Buscando usuario {} en la base de datos", uid);
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
                log.debug("Role asignado al usuario {}", uid);

                UserPreferences preferences = new UserPreferences();
                user.setPreferences(preferences);
                preferences.setUser(user);
                log.debug("Preferences inicializadas para usuario {}", uid);

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
        log.debug("Obteniendo perfil de usuario {}", uid);
        UserProfile userData = repoProfile.findById(uid)
                        .orElseThrow(() -> {
                            log.error("Usuario {} no encontrado al obtener perfil", uid);
                            return new RuntimeException("Usuario no encontrado");
                        });

        log.debug("Perfil de usuario {} obtenido correctamente", uid);
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
    public UserProfileDTO updateUserSelf(String uid, UserProfileDTO userDTO){
        log.info("Actualizando perfil de usuario {}", uid);
        UserProfile userData = repoProfile.findById(uid)
                .orElseThrow(() -> {
                    log.error("Usuario {} no encontrado al intentar actualizar perfil", uid);
                    return new RuntimeException("Usuario no encontrado");
                });

        if (userDTO.getFirstName() != null ) userData.setFirstName(userDTO.getFirstName());
        if (userDTO.getLastName() != null ) userData.setLastName(userDTO.getLastName());
        if (userDTO.getPhone() != null ) userData.setPhone(userDTO.getPhone());
        if (userDTO.getAvatarUrl() != null ) userData.setAvatarUrl(userDTO.getAvatarUrl());
        if (userDTO.getBirthday() != null ) userData.setBirthday(userDTO.getBirthday());
        if (userDTO.getBio() != null ) userData.setBio(userDTO.getBio());

        repoProfile.save(userData);
        log.info("Perfil de usuario {} actualizado correctamente", uid);

        return new UserProfileDTO(userData);
    }

    @Transactional
    public void changeEmail(String uid, String email) {
        log.info("Actualizando email de usuario {} a {}", uid, email);
        User userOpt = repoUser.findById(uid)
            .orElseThrow(() -> {
                log.error("Usuario {} no encontrado al intentar cambiar email", uid);
                return new RuntimeException("Usuario no encontrado");
            });

        try {
            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
                    .setEmail(email);

            FirebaseAuth.getInstance().updateUser(request);
            log.debug("Email actualizado en Firebase para usuario {}", uid);

            userOpt.setEmail(email);
            repoUser.save(userOpt);
            log.info("Email actualizado en base de datos para usuario {}", uid);
        } catch (FirebaseAuthException e) {
            log.error("Error al actualizar usuario {} ", uid, e);

            if ("EMAIL_ALREADY_EXISTS".equals(e.getErrorCode())) {
                throw new RuntimeException("El correo ya está en uso por otro usuario");
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Transactional
    public void deactivateUser(String uid) throws FirebaseAuthException {
        log.info("Desactivando usuario {}", uid);
        User userData = repoUser.findById(uid)
            .orElseThrow(() -> {
                log.error("Usuario {} no encontrado al intentar desactivar", uid);
                return new RuntimeException("Usuario no encontrado");
            });

        FirebaseAuth.getInstance().deleteUser(uid);
        log.info("Usuario {} eliminado de Firebase correctamente", uid);

        repoUser.delete(userData);
        log.debug("Usuario {} eliminado de base de datos", uid);
    }


}