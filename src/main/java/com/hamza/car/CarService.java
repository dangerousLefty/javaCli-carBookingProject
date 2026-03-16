package com.hamza.car;

import java.util.NoSuchElementException;
import java.util.UUID;

public class CarService {
    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public Car getCar(UUID id){
        return carDAO.findCarById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ Car not found with id ".concat(id.toString())
                ));
    }

    public Car[] getCars(){
        return carDAO.getCars();
    }

    public Car[] getAvailableCars(){
        return carDAO.getAvailableCars();
    }

    public Car[] getAvailableCarsByType(CarType type){
        return carDAO.getAvailableCarByType(type);
    }

}
