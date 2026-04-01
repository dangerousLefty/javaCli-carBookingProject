package com.hamza.car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarDAOLists {
    List<Car> getAvailableCars();
    List<Car> getAvailableCarByType(CarType type);
    Optional<Car> findCarById(UUID carId);

}
