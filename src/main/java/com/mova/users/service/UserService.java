// service/UserService.java
package com.mova.users.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.mova.users.model.Role;
import com.mova.users.model.User;
import com.mova.users.model.UserPreferences;
import com.mova.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repo;
    private UserPreferences userPreferences;
    public UserService(UserRepository repo) { this.repo = repo; }

    @Transactional
    public User getOrProvision(String uid) throws Exception {
        return repo.findById(uid).orElseGet(() -> {
            try {
                UserRecord fr = FirebaseAuth.getInstance().getUser(uid);

                User u = new User();
                u.setUid(fr.getUid());
                u.setEmail(fr.getEmail());
                u.setIsActive(true);

                Role role = new Role(u);
                u.setRole(role);

                UserPreferences prefs = new UserPreferences();
                prefs.setUser(u);
                u.setPreferences(prefs);

                return repo.save(u);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public User save(User u) { return repo.save(u); }

}