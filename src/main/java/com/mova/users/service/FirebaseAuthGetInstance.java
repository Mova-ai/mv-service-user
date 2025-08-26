package com.mova.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mova.users.dto.UserDto;

public class FirebaseAuthGetInstance {

    public UserDto getInstance(String uidUser){
        try {
        UserRecord fr = FirebaseAuth.getInstance().getUser(uidUser);
            System.out.println("FR COMPLETO: \n \t" + fr);


            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fr);
            System.out.println(json);


        UserDto dto = new UserDto();
        dto.setEmail(fr.getEmail());
        return dto;
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
