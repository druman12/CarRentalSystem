package com.CarService.Repository;

import com.CarService.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findByCompanyId(int companyId);

}
