package com.example.demo.controller;

import com.example.demo.dao.CarDaoSQL;
import com.example.demo.dao.UserDaoSQL;
import com.example.demo.model.CarModel;
import com.example.demo.model.request.AddCarRequestModel;
import com.example.demo.model.request.CarRequestModel;
import com.example.demo.model.request.CarSearchModel;
import com.example.demo.model.request.UpdateCarInfoRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class CarController {
    private static final CarDaoSQL cd = new CarDaoSQL();
    private static final UserDaoSQL ud = new UserDaoSQL();


    @GetMapping("cars")
    public List<CarModel> getAllCars() { return cd.getAllCars();}

    @GetMapping("cars/search")
    public List<CarModel> getCars(@RequestParam(required = false) String make,
                                  @RequestParam(required = false) String model,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) String automatic,
                                  @RequestParam(required = false) Double price,
                                  @RequestParam(required = false) Integer power,
                                  @RequestParam(required = false) Integer doors) {

        if (automatic == null) {}
        else if (!Boolean.parseBoolean(automatic) && !automatic.equals("false")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return cd.getAllCars(new CarSearchModel(year,doors,power,make,model,automatic,price));
    }

    @GetMapping("/cars/{id}")
    public CarModel getCarById(@PathVariable("id") String carId) {return cd.getCar(UUID.fromString(carId)); }

    @PatchMapping("/cars/{carId}")
    public void updateCar(@PathVariable("carId") String carID,
                          @RequestHeader("authorization") String adminID,
                          @RequestBody UpdateCarInfoRequestModel carInfo){

        if (!ud.isAdmin(UUID.fromString(adminID))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        cd.update(new CarModel(UUID.fromString(carID), carInfo));
    }


    @DeleteMapping("/cars/{carId}")
    public void deleteCar(@PathVariable("carId") String carId,
                          @RequestHeader("authorization") String adminID){

        if (!ud.isAdmin(UUID.fromString(adminID))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        cd.delete(UUID.fromString(carId));
    }

    @PostMapping("/cars")
    public void addCar(@RequestHeader("authorization") String adminId,
                       @RequestBody CarRequestModel car){

        if (!ud.isAdmin(UUID.fromString(adminId))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        cd.add(new CarModel(car));
    }

    @GetMapping("cars/available")
    public List<CarModel> getAvailableCars(@RequestParam String startDate,@RequestParam String endDate) {
        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = LocalDate.parse(endDate);

        return cd.getAllAvailableCars(sd,ed);

    }

    @GetMapping("cars/available/search")
    public List<CarModel> searchAvailableCars(@RequestParam String startDate,
                                              @RequestParam String endDate,
                                              @RequestParam(required = false) String make,
                                              @RequestParam(required = false) String model,
                                              @RequestParam(required = false) Integer year,
                                              @RequestParam(required = false) String automatic,
                                              @RequestParam(required = false) Double price,
                                              @RequestParam(required = false) Integer power,
                                              @RequestParam(required = false) Integer doors) {

        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = LocalDate.parse(endDate);

        if (automatic == null) {}
        else if (!Boolean.parseBoolean(automatic) && !automatic.equals("false")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return cd.getAllAvailableCars(sd,ed,new CarSearchModel(year,doors,power,make,model,automatic,price));

    }

    @GetMapping("/cars/{carId}/calendar")
    public List<String> unavailableDates(@PathVariable String carId){
        return cd.getUnavailableDates(UUID.fromString(carId));
    }

    @GetMapping("/test")
    public CarRequestModel Test(@RequestBody CarRequestModel carInfo){
        return new CarRequestModel(carInfo);
    }

    @GetMapping("/test2")
    public CarSearchModel Test2(@RequestParam String startDate,
                                 @RequestParam String endDate,
                                 @RequestParam(required = false) String make,
                                 @RequestParam(required = false) String model,
                                 @RequestParam(required = false) Integer year,
                                 @RequestParam(required = false) String automatic,
                                 @RequestParam(required = false) Double price,
                                 @RequestParam(required = false) Integer power,
                                 @RequestParam(required = false) Integer doors){

        LocalDate sd = LocalDate.parse(startDate);
        LocalDate ed = LocalDate.parse(endDate);


        return new CarSearchModel(year,doors,power,make,model,automatic,price);
    }


}
