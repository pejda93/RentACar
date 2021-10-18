package com.example.demo.model.request;

public class AdminApprovalContact {
    boolean approved;

    public AdminApprovalContact(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }
}
