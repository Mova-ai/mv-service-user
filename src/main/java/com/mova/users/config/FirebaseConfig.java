package com.mova.users.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials.path}")
    private String firebaseCredentialsPath;

    @PostConstruct
    public void init() throws Exception {
        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream(firebaseCredentialsPath);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado correctamente!");
        }
    }
}
