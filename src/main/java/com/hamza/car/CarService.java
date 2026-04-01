package com.hamza.car;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class CarService {
    private final CarDAOLists carDAO;

    public CarService(CarDAOLists carDAO) {
        this.carDAO = carDAO;
    }

    public Car getCar(UUID id){
        return carDAO.findCarById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ Car not found with id ".concat(id.toString())
                ));
    }

    public List<Car> getAvailableCars(){
        return carDAO.getAvailableCars();
    }
    public List<Car> getAvailableCarsByType(CarType type){
        return carDAO.getAvailableCarByType(type);
    }

}
