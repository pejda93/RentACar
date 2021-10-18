package com.example.demo.model;

import java.time.LocalDate;
import java.util.UUID;

public class ContractsModel {
    private UUID contract_id, user_id, car_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private Double total_price;
    private boolean signed, approved;

    public ContractsModel(UUID user_id, UUID car_id, LocalDate start_date, LocalDate end_date, Double total_price, boolean signed, boolean approved) {
        this.contract_id = UUID.randomUUID();
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_price = total_price;
        this.signed = signed;
        this.approved = approved;
    }

    public ContractsModel(UUID contract_id, UUID user_id, UUID car_id, LocalDate start_date, LocalDate end_date, Double total_price, boolean signed, boolean approved) {
        this.contract_id = contract_id;
        this.user_id = user_id;
        this.car_id = car_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_price = total_price;
        this.signed = signed;
        this.approved = approved;
    }

    public ContractsModel(ContractsModel cm) {
        this.contract_id = cm.contract_id;
        this.user_id = cm.user_id;
        this.car_id = cm.car_id;
        this.start_date = cm.start_date;
        this.end_date = cm.end_date;
        this.total_price = cm.total_price;
        this.signed = cm.signed;
        this.approved = cm.approved;
    }


    public ContractsModel(UUID contract_id, ContractsModel cm) {
        this.contract_id = contract_id;
        this.user_id = cm.user_id;
        this.car_id = cm.car_id;
        this.start_date = cm.start_date;
        this.end_date = cm.end_date;
        this.total_price = cm.total_price;
        this.signed = cm.signed;
    }

    public ContractsModel(UUID contract_id) {
        this.contract_id = contract_id;
    }

    public ContractsModel() {
    }

    public UUID getContract_id() {
        return contract_id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public UUID getCar_id() {
        return car_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isApproved() {
        return approved;
    }
}
