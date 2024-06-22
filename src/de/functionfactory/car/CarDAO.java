package de.functionfactory.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CarDAO {
    private static final List<Car> CARS = Arrays.asList(
            new Car("123", new BigDecimal("100"), Brand.MERCEDES, false),
            new Car("456", new BigDecimal("80.99"), Brand.TESLA, true),
            new Car("5678", new BigDecimal("77.00"), Brand.MERCEDES, false),
            new Car("1234", new BigDecimal("79.99"), Brand.HYUNDAII, true)
    );

    public List<Car> getAllCars() {
        return CARS;
    }
}

