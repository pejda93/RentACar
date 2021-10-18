package com.example.demo.controller;

import com.example.demo.dao.ContractDaoSQL;
import com.example.demo.dao.UserDaoSQL;
import com.example.demo.model.ContractsModel;
import com.example.demo.model.request.ContractApprovalModel;
import com.example.demo.model.request.RegisterRequestModel;
import com.example.demo.model.response.ContractSampleResponseModel;
import com.example.demo.model.response.RegisterResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class ContractController {
    private static final ContractDaoSQL cd = new ContractDaoSQL();
    private static final UserDaoSQL ud = new UserDaoSQL();


    @PostMapping("/contracts/sample")
    public ContractSampleResponseModel contractSample(@RequestBody ContractSampleResponseModel cm){
        return new ContractSampleResponseModel(cm);
    }


    @PostMapping("/contracts")
    public RegisterResponseModel addContract(@RequestBody ContractsModel cm,
                                             @RequestHeader(value = "authorization", required = false) String adminId){

        if (cm.isApproved() && !ud.isAdmin(UUID.fromString(adminId))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return cd.add(new ContractsModel(UUID.randomUUID(),cm));
    }

    @GetMapping("/contracts")
    public List<ContractsModel> getAllContracts(@RequestHeader("authorization") String adminId){
        return cd.getAllContracts();
    }

    @GetMapping("/contracts/pending")
    public List<ContractsModel> getAllPendingContracts(@RequestHeader("authorization") String adminId){
        return cd.getAllPendingContracts();
    }

    @PostMapping("/contracts/{id}/approval")
    public void approveContract(@RequestBody ContractApprovalModel cam,
                                @RequestHeader("authorization") String adminId,
                                @PathVariable("id") String contractId){

        if (cam.isApproved() && !ud.isAdmin(UUID.fromString(adminId))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        cd.ContractApproval(cam.isApproved(), contractId);

    }

    @GetMapping("/contracts/{id}/history")
    public List<ContractsModel> getAllUserContracts(@PathVariable("id") String contractId){
        return cd.getAllUserContracts(contractId);
    }

}
