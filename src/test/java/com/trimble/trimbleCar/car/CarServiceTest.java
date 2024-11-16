package com.trimble.trimbleCar.car;

import com.trimble.trimbleCar.carOwner.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class CarServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceTest.class);

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCar() {
        Car car = Car.builder()
                .make("Tesla")
                .model("Model S")
                .year(2023)
                .status("Ideal")
                .build();

        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car savedCar = carService.registerCar(car);
        assertNotNull(savedCar);
        assertEquals("Tesla", savedCar.getMake());
        assertEquals("Model S", savedCar.getModel());

        verify(carRepository, times(1)).save(car);  // Verify that save was called once
    }

    @Test
    public void testGetCarsByStatus() {
        Car car1 = Car.builder().status("Ideal").build();
        Car car2 = Car.builder().status("On Lease").build();
        List<Car> cars = List.of(car1, car2);

        when(carRepository.findByStatus("Ideal")).thenReturn(List.of(car1));

        List<Car> result = carService.getCarsByStatus("Ideal");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ideal", result.get(0).getStatus());

        verify(carRepository, times(1)).findByStatus("Ideal");
    }

    @Test
    public void testGetCarsByOwner() {
        Long ownerId = 1L;
        Car car1 = new Car();
        car1.setOwner(new Owner());
        car1.getOwner().setId(Math.toIntExact(ownerId));

        List<Car> cars = List.of(car1);
        when(carRepository.findByOwnerId(ownerId)).thenReturn(cars);

        List<Car> result = carService.getCarsByOwner(ownerId);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(ownerId, result.get(0).getOwner().getId());
    }

    }

}
