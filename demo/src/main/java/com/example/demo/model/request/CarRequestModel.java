package com.example.demo.model.request;

import java.util.UUID;

public class CarRequestModel {
    private String licence_plate, make, model, color, fuel, image;
    private int year, engine_capacity, doors, power;
    private double price;
    private String size;
    private boolean automatic;

    public CarRequestModel(String licence_plate, String make, String model, String color, String fuel, String image, int year, int engine_capacity, int doors, int power, double price, String size, boolean automatic) {
        this.licence_plate = licence_plate;
        this.make = make;
        this.model = model;
        this.color = color;
        this.fuel = fuel;
        this.image = image;
        this.year = year;
        this.engine_capacity = engine_capacity;
        this.doors = doors;
        this.power = power;
        this.price = price;
        this.size = size;
        this.automatic = automatic;
    }

    public CarRequestModel(CarRequestModel car) {
        this.licence_plate = car.licence_plate;
        this.make = car.make;
        this.model = car.model;
        this.color = car.color;
        this.fuel = car.fuel;
        this.image = car.image;
        this.year = car.year;
        this.engine_capacity = car.engine_capacity;
        this.doors = car.doors;
        this.power = car.power;
        this.price = car.price;
        this.size = car.size;
        this.automatic = car.automatic;
    }



    public CarRequestModel() {
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getFuel() {
        return fuel;
    }

    public String getImage() {
        return image;
    }

    public int getYear() {
        return year;
    }

    public int getEngine_capacity() {
        return engine_capacity;
    }

    public int getDoors() {
        return doors;
    }

    public int getPower() {
        return power;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public boolean isAutomatic() {
        return automatic;
    }
}
