package com.trimble.trimbleCar.lease;

import com.trimble.trimbleCar.car.Car;
import com.trimble.trimbleCar.car.CarService;
import com.trimble.trimbleCar.user.User;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LeaseService {
    private static final Logger logger = LoggerFactory.getLogger(LeaseService.class);


    private LeaseRepository leaseRepository;
    private CarService carService;

    public LeaseService(LeaseRepository leaseRepository, CarService carService) {
        this.leaseRepository = leaseRepository;
        this.carService = carService;
    }

    public Lease startLease(Lease lease) {


        return leaseRepository.save(lease);
    }

    public void endLease(Long leaseId) {
        leaseRepository.deleteById(leaseId);
    }

    public List<Lease> getLeaseHistory(Long customerId) {
        return leaseRepository.findByCustomerId(customerId);
    }
    public List<Lease> getLeaseHistoryForOwner(Long ownerId) {
        return leaseRepository.findByCarOwnerId(ownerId);
    }


    public Lease startLease(Integer customerId, Integer carId) {
        // First, check if the customer already has 2 active leases
        if (hasMaxActiveLeases(customerId)) {
            throw new IllegalArgumentException("Customer cannot lease more than 2 cars at a time.");
        }

        // Retrieve the car to ensure it's available
        Car car = carService.getCarById(Long.valueOf(carId))
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        // If the car is already on lease, we throw an error
        if ("On Lease".equals(car.getStatus())) {
            throw new IllegalArgumentException("Car is already on lease.");
        }

        // Update car status to "On Lease"
        car.setStatus("On Lease");

        // Create a new lease record
        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(new User());// Assuming you have a customer constructor with ID
        lease.setStartDate(LocalDate.now());
        lease.setEndDate(null); // End date will be set when the lease ends

        // Save the lease and update the car status
        leaseRepository.save(lease);
        carService.registerCar(car); // Save the car with updated status

        return lease;
    }

    private boolean hasMaxActiveLeases(Integer customerId) {
        // Find active leases (those that are not yet ended)
        List<Lease> activeLeases = leaseRepository.findByCustomerIdAndEndDateIsNull(customerId);
        return activeLeases.size() >= 2;
    }
}