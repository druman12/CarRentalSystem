package com.CarService.Service;

import com.CarService.Entity.Car;
import com.CarService.Repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> getCarByCompanyId(int companyId) {
        return carRepository.findByCompanyId(companyId);
    }


    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(int id, Car carDetails) {
        return carRepository.findById(id).map(car -> {
            if (carDetails.getCompanyId() != 0) {
                car.setCompanyId(carDetails.getCompanyId());
            }
            if (carDetails.getMake() != null && !carDetails.getMake().isEmpty()) {
                car.setMake(carDetails.getMake());
            }
            if (carDetails.getModel() != null && !carDetails.getModel().isEmpty()) {
                car.setModel(carDetails.getModel());
            }
            if (carDetails.getYear() != 0) {
                car.setYear(carDetails.getYear());
            }
            if (carDetails.getCategory() != null && !carDetails.getCategory().isEmpty()) {
                car.setCategory(carDetails.getCategory());
            }
            if (carDetails.getDailyRate() != 0) {
                car.setDailyRate(carDetails.getDailyRate());
            }
            if (carDetails.getFuelType() != null && !carDetails.getFuelType().isEmpty()) {
                car.setFuelType(carDetails.getFuelType());
            }
            if (carDetails.getSeatingCapacity() != 0) {
                car.setSeatingCapacity(carDetails.getSeatingCapacity());
            }
            if (carDetails.getFeatures() != null && !carDetails.getFeatures().isEmpty()) {
                car.setFeatures(carDetails.getFeatures());
            }
            if (carDetails.getImageUrls() != null && !carDetails.getImageUrls().isEmpty()) {
                car.setImageUrls(carDetails.getImageUrls());
            }
            if (carDetails.getStatus() != null && !carDetails.getStatus().isEmpty()) {
                car.setStatus(carDetails.getStatus());
            }
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    @Override
    public void deleteCar(int id) {
        carRepository.deleteById(id);
    }
}
