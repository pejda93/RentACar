package com.example.demo.dao;

import com.example.demo.db.DatabaseConnection;
import com.example.demo.model.CarModel;
import com.example.demo.model.request.CarRequestModel;
import com.example.demo.model.request.CarSearchModel;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CarDao {
    List<CarModel> getAllCars();
    List<CarModel> getAllCars(CarSearchModel car);
    CarModel getCar(UUID carId);
    void update(CarModel cm);
    void delete(UUID carId);
    void add(CarModel cm);
    List<CarModel> getAllAvailableCars(LocalDate start_date, LocalDate end_date);
    List<CarModel> getAllAvailableCars(LocalDate start_date, LocalDate end_date, CarSearchModel car);
    List<String> getUnavailableDates(UUID carId);

}
