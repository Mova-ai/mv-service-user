// controller/UserController.java
package com.mova.users.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.mova.users.dto.UserProfileDTO;
import com.mova.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService users) { this.service = users; }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getUserSelf(Authentication auth) {
        String uid = (String) auth.getPrincipal();
        UserProfileDTO profile = service.getUserSelf(uid);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDTO>
        updateUserSelf(Authentication auth, @RequestBody UserProfileDTO user){
        String uid = (String) auth.getPrincipal();
        return ResponseEntity.ok(service.updateUserSelf(uid, user));
    }

    @DeleteMapping("/me/deactivate")
    public ResponseEntity<?> desactivateUserSelf(Authentication auth) {
        String uid = (String) auth.getPrincipal();
        try {
            service.deactivateUser(uid);
            return ResponseEntity.ok().build();
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar en Firebase: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }
}
