package com.example.demo.model.request;

import java.util.UUID;

public class RegisterRequestModel {
    private String username, password, email;
    private UUID user_id;

    public RegisterRequestModel(String username, String password, String email, UUID user_id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.user_id = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UUID getUser_id() {
        return user_id;
    }
}
