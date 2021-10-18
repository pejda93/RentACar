package com.example.demo.dao;

import com.example.demo.model.UserModel;
import com.example.demo.model.request.LoginRequestModel;
import com.example.demo.model.request.RegisterRequestModel;
import com.example.demo.model.response.LoginResponseModel;
import com.example.demo.model.response.RegisterResponseModel;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    RegisterResponseModel registerUser(RegisterRequestModel rrm);
    LoginResponseModel loginUser(LoginRequestModel lrm);
    LoginResponseModel update(UserModel um, String newPassword);
    UserModel getUser(UUID userId);
    List<UserModel> getAllUsers();
    boolean isAdmin(UUID userId);
}
