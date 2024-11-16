package com.trimble.trimbleCar.car;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {


    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/register")
    public Car registerCar(@RequestBody Car car) {
        return carService.registerCar(car);
    }

    @GetMapping("/status/{status}")
    public List<Car> getCarsByStatus(@PathVariable String status) {
        return carService.getCarsByStatus(status);
    }
    @PostMapping("/car-details/{carId}")
    public Optional<Car> carDetatils(@RequestBody @PathVariable("carId") Integer carId, Authentication connectedUser) {
        return carService.getCarDetails(carId);
    }
}