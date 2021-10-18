package com.example.demo.controller;

import com.example.demo.dao.UserDaoSQL;
import com.example.demo.model.UserModel;
import com.example.demo.model.request.AdminUpdateUserInfoRequestModel;
import com.example.demo.model.request.UpdateUserInfoRequestModel;
import com.example.demo.model.response.LoginResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class AdminController {
    private static final UserDaoSQL ud = new UserDaoSQL();

    @PatchMapping("/admin/update/{id}")
    public LoginResponseModel updateUser(@PathVariable("id") String userID,
                                         @RequestHeader(value = "authorization", required = false) String adminID,
                                         @RequestBody AdminUpdateUserInfoRequestModel userInfo){

        if ((userInfo.getEmail() != null || userInfo.getPersonal_number() != null || userInfo.isAdmin()) && !ud.isAdmin(UUID.fromString(adminID))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return ud.AdminUpdate(new UserModel(UUID.fromString(userID), userInfo));
    }
}
