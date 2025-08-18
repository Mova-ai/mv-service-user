// controller/UserController.java
package com.mova.users.controller;

import com.mova.users.model.User;
import com.mova.users.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class UserController {
    private final UserService users;
    public UserController(UserService users) { this.users = users; }

    // GET /me  → devuelve perfil (y crea si no existe)
    @GetMapping
    public User me(Authentication auth) throws Exception {
        System.out.println("Entro al controller");
        System.out.println("Auth del controller: " + auth);
        String uid = (String) auth.getPrincipal();
        return users.getOrProvision(uid);
    }
/*
    // PUT /me  → actualizar displayName, photo, prefs...
    @PutMapping
    public User updateMe(Authentication auth, @RequestBody UpdateMeDto dto) throws Exception {
        String uid = (String) auth.getPrincipal();
        User u = users.getOrProvision(uid);
        if (dto.displayName() != null) u.setDisplayName(dto.displayName());
        if (dto.photoUrl() != null) u.setPhoto(dto.photoUrl());
        if (dto.preferences() != null) {
            var p = u.getPreferences();
            if (dto.preferences().language() != null) p.setLanguage(dto.preferences().language());
            if (dto.preferences().darkMode() != null) p.setDarkMode(dto.preferences().darkMode());
            if (dto.preferences().currency() != null) p.setCurrency(dto.preferences().currency());
        }
        return users.save(u);
    }


 */
}

// DTOs
record PreferencesDto(String language, Boolean darkMode, String currency) {}
record UpdateMeDto(String displayName, String photoUrl, PreferencesDto preferences) {}

