// service/UserService.java
package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.mova.users.model.Role;
import com.mova.users.model.User;
import com.mova.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    @Transactional
    public User getOrProvision(String uid) throws Exception {
        return repo.findById(uid).orElseGet(() -> {
            try {
                UserRecord fr = FirebaseAuth.getInstance().getUser(uid);
                User u = new User();
                u.setUid(fr.getUid());
                u.setEmail(fr.getEmail());
                u.setDisplayName(fr.getDisplayName());
                u.setPhoto(fr.getPhotoUrl());
                u.setRoles(Set.of(defaultRole()));
                return repo.save(u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Role defaultRole() {
        Role r = new Role();
        r.setName("USER");
        return r;
    }
}