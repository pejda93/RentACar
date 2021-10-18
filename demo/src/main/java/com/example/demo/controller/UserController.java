package com.example.demo.controller;

import com.example.demo.dao.UserDaoSQL;
import com.example.demo.model.CarModel;
import com.example.demo.model.UserModel;
import com.example.demo.model.request.*;
import com.example.demo.model.response.LoginResponseModel;
import com.example.demo.model.response.RegisterResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private static final UserDaoSQL ud = new UserDaoSQL();

    @PostMapping("/users/register")
    public RegisterResponseModel registerUser(@RequestBody RegisterRequestModel user){
            return ud.registerUser(user);
    }

    @PostMapping("/users/login")
    public LoginResponseModel loginUser(@RequestBody LoginRequestModel user){
        return ud.loginUser(user);
    }

    @PatchMapping("/users/{id}")
    public LoginResponseModel updateUser(@PathVariable("id") String userID,
                                          @RequestHeader(value = "authorization", required = false) String adminID,
                                          @RequestBody UpdateUserInfoRequestModel userInfo){

        if ((userInfo.getEmail() != null || userInfo.getPersonal_number() != null || userInfo.isAdmin()) && !ud.isAdmin(UUID.fromString(adminID))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return ud.update(new UserModel(UUID.fromString(userID), userInfo),userInfo.getNewPassword());
    }

    @GetMapping("/users/{id}")
    public UserModel getUserById(@PathVariable("id") String userId) {return ud.getUser(UUID.fromString(userId)); }

    @GetMapping("/users")
    public List<UserModel> getAllUsers() { return ud.getAllUsers();}


}
