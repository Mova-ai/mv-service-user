package com.mova.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import com.mova.users.model.User;
import com.mova.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication auth) throws Exception{
        String uid = (String) auth.getPrincipal();
        User user = userService.getUserOrCreate(uid);

        log.info("Usuario {} logueado correctamente", uid);
        return ResponseEntity.ok(user);
    };
}
