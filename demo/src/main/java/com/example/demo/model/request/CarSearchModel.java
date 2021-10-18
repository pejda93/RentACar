package com.example.demo.model.request;

public class CarSearchModel {
    private Integer year, doors, power;
    private String make, model, automatic;
    private Double price;

    public CarSearchModel(Integer year, Integer doors, Integer power, String make, String model, String automatic, Double price) {
        this.year = year;
        this.doors = doors;
        this.power = power;
        this.make = make;
        this.model = model;
        this.automatic = automatic;
        this.price = price;
    }

    public CarSearchModel(String make) {
        this.make = make;
    }



    public CarSearchModel() {
    }

    public Integer getYear() {
        return year;
    }

    public Integer getDoors() {
        return doors;
    }

    public Integer getPower() {
        return power;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getAutomatic() {
        return automatic;
    }

    public Double getPrice() {
        return price;
    }
}
