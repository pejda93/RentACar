package com.example.demo.model.request;

public class ContractApprovalModel {
    boolean approved;

    public ContractApprovalModel(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public ContractApprovalModel() {
    }
}
