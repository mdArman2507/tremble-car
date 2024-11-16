package com.trimble.trimbleCar.car;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public Car registerCar(Car car) {
        logger.info("Attempting to registerCar {} ",car);
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        try {
            return carRepository.save(car);
        } catch (Exception e) {
            logger.error("Error occurred while registering car: {}", e.getMessage());
            throw new RuntimeException("Failed to register car", e);
        }
    }

    public List<Car> getCarsByStatus(String status) {
        logger.info("Attempting to getCarsByStatus {} ",status);
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
        try {
            return carRepository.findByStatus(status);
        } catch (Exception e) {
            logger.error("Error occurred while fetching cars by status: {}", status, e);
            throw new RuntimeException("Failed to fetch cars", e);
        }
    }

    public Optional<Car> getCarById(Long carId) {
        logger.info("Attempting to getCarById {} ",carId);
        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }
        return carRepository.findById(carId);
    }
    public Optional<Car> getCarDetails(Integer carId) {
        logger.info("Attempting to getCarDetails {} and {} ",carId, carId);
        if (carId == null || carId == null) {
            throw new IllegalArgumentException("Owner ID and Car ID cannot be null");
        }
        return Optional.ofNullable(carRepository.findByCarId(carId));
    }
    public List<Car> getCarsByOwner(Long ownerId) {
        logger.info("Attempting to getCarsByOwner {} ",ownerId);
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        try {
            return carRepository.findByOwnerId(ownerId);
        } catch (Exception e) {
            logger.error("Error occurred while fetching cars for owner: {}", ownerId, e);
            throw new RuntimeException("Failed to fetch cars for owner", e);
        }
    }

}
