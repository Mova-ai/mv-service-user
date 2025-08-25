package com.mova.users.controller;

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

    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public ResponseEntity<?> login(Authentication auth) throws Exception{
        String uid = (String) auth.getPrincipal();
        User user = userService.getUserOrCreate(uid);
        return ResponseEntity.ok(user);
    };
}
