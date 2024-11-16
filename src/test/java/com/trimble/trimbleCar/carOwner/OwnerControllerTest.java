package com.trimble.trimbleCar.carOwner;

import com.trimble.trimbleCar.car.Car;
import com.trimble.trimbleCar.car.CarService;
import com.trimble.trimbleCar.lease.Lease;
import com.trimble.trimbleCar.lease.LeaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private LeaseService leaseService;

    @InjectMocks
    private OwnerController ownerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCar() {
        Car car = new Car();
        car.setMake("Tesla");
        car.setModel("Model 3");

        when(carService.registerCar(any(Car.class))).thenReturn(car);

        ResponseEntity<Car> response = ownerController.registerCar(1, car);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Tesla", response.getBody().getMake());
    }

    @Test
    public void testGetCarsByOwner() {
        when(carService.getCarsByOwner(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Car>> response = ownerController.getCars(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testGetLeaseHistory() {
        when(leaseService.getLeaseHistoryForOwner(anyLong())).thenReturn(Collections.emptyList());

        ResponseEntity<List<Lease>> response = ownerController.getLeaseHistory(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
