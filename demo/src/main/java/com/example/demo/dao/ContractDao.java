package com.example.demo.dao;

import com.example.demo.model.ContractsModel;
import com.example.demo.model.request.ContractApprovalModel;
import com.example.demo.model.response.RegisterResponseModel;

import java.util.List;

public interface ContractDao {
    RegisterResponseModel add(ContractsModel cm);
    List<ContractsModel> getAllContracts();
    List<ContractsModel> getAllPendingContracts();
    void ContractApproval(boolean isApproved, String contractId);
    List<ContractsModel> getAllUserContracts(String userId);

}
