// controller/UserController.java
package com.mova.users.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.mova.users.dto.UserProfileDTO;
import com.mova.users.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public UserController(UserService users) { this.service = users; }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getUserSelf(Authentication auth) {
        String uid = (String) auth.getPrincipal();
        UserProfileDTO profile = service.getUserSelf(uid);

        log.info("Perfil de {} obtenido correctamente", uid);

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO>
        updateUserSelf(Authentication auth, @RequestBody UserProfileDTO user){
        String uid = (String) auth.getPrincipal();

        log.info("Perfil de {} actualizado correctamente", uid);

        return ResponseEntity.ok(service.updateUserSelf(uid, user));
    }

    @PatchMapping("/me/changeEmail")
    public ResponseEntity<?> changeEmailUser(Authentication auth, @RequestBody String email){
        String uid = (String) auth.getPrincipal();
        service.changeEmail(uid, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me/deactivate")
    public ResponseEntity<?> desactivateUserSelf(Authentication auth) {
        String uid = (String) auth.getPrincipal();
        log.info("DELETE /user/me/deactivate → desactivando usuario {}", uid);

        try {
            service.deactivateUser(uid);
            log.info("Usuario {} desactivado correctamente", uid);
            return ResponseEntity.ok().build();
        } catch (FirebaseAuthException e) {
            log.error("Error al eliminar en Firebase para {}: {}", uid, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar en Firebase: " + e.getMessage());
        } catch (RuntimeException e) {
            log.error("Usuario {} no encontrado al intentar desactivar", uid);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }
}
