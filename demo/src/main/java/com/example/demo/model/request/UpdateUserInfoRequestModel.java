package com.example.demo.model.request;

public class UpdateUserInfoRequestModel {
    private String username, email, password, newPassword, first_name, last_name, phone_number, personal_number, image;
    private boolean admin;

    public UpdateUserInfoRequestModel(String username, String email, String password, String newPassword, String first_name, String last_name, String phone_number, String personal_number, String image, boolean admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.newPassword = newPassword;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.personal_number = personal_number;
        this.image = image;
        this.admin = admin;
    }

    public UpdateUserInfoRequestModel() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPersonal_number() {
        return personal_number;
    }

    public String getImage() {
        return image;
    }

    public boolean isAdmin() {
        return admin;
    }
}
