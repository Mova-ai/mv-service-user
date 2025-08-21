package com.mova.users.controller;

import com.mova.users.model.UserProfile;
import com.mova.users.service.UserProfileService;
import com.mova.users.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    public final UserProfileService service;

    public UserProfileController(UserProfileService service){
        this.service = service;
    }

    @GetMapping
    public UserProfile getUserProfile(Authentication auth) throws Exception{
        System.out.println("Hola desde GetUserProfile");
        String uid = (String) auth.getPrincipal();
        System.out.println(uid);
        return service.getUserProfile(uid);
    }
}
