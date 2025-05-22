package com.CarService.Service;

import com.CarService.Entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();

    Optional<Car> getCarById(int id);

    List<Car> getCarByCompanyId(int companyId);

    Car addCar(Car car);

    Car updateCar(int id, Car carDetails);

    void deleteCar(int id);
}