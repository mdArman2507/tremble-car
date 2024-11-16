package com.trimble.trimbleCar.carOwner;




import com.trimble.trimbleCar.car.Car;
import com.trimble.trimbleCar.car.CarService;
import com.trimble.trimbleCar.lease.Lease;
import com.trimble.trimbleCar.lease.LeaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {


    private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);
    private CarService carService;


    private LeaseService leaseService;

    public OwnerController(CarService carService, LeaseService leaseService) {
        this.carService = carService;
        this.leaseService = leaseService;
    }


    @PostMapping("/{ownerId}/register-car")
    public ResponseEntity<Car> registerCar(@PathVariable Integer ownerId, @RequestBody Car car) {
        try {
            logger.info("Attempting to register car for owner: {}", ownerId);
            car.setId(ownerId); // Set ownerId for the car
            Car savedCar = carService.registerCar(car);
            logger.info("Successfully registered car: {}", savedCar);
            return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while registering car for owner: {}", ownerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{ownerId}/cars")
    public ResponseEntity<List<Car>> getCars(@PathVariable Long ownerId) {
        try {
            logger.info("Fetching cars for owner: {}", ownerId);
            List<Car> cars = carService.getCarsByOwner(ownerId);
            if (cars.isEmpty()) {
                logger.info("No cars found for owner: {}", ownerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Successfully fetched cars for owner: {}", ownerId);
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching cars for owner: {}", ownerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{ownerId}/lease-history")
    public ResponseEntity<List<Lease>> getLeaseHistory(@PathVariable Long ownerId) {
        try {
            logger.info("Fetching lease history for owner: {}", ownerId);
            List<Lease> leaseHistory = leaseService.getLeaseHistoryForOwner(ownerId);
            if (leaseHistory.isEmpty()) {
                logger.info("No lease history found for owner: {}", ownerId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Successfully fetched lease history for owner: {}", ownerId);
            return new ResponseEntity<>(leaseHistory, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching lease history for owner: {}", ownerId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

