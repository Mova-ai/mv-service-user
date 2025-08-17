// config/FirebaseConfig.java
package com.mova.users.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {


    public FirebaseConfig() throws Exception {
        if (FirebaseApp.getApps().isEmpty()) {
            String json = System.getenv("MV-FIREBASE-APPLICATION_CREDENTIALS");
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))))
                    .build();
            FirebaseApp.initializeApp(options);
        }
    }
}
