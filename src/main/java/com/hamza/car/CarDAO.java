package com.hamza.car;

import java.util.Optional;
import java.util.UUID;

public interface CarDAO {
    Car[] getAvailableCars();
    Car[] getAvailableCarByType(CarType type);
    Optional<Car> findCarById(UUID carId);

}
