package com.hamza.car;

import java.util.NoSuchElementException;
import java.util.UUID;

public class CarService {
    private final CarDAO carDAO = new CarDAO();

    public Car getCar(UUID id, boolean isBooked){
        return carDAO.findCarById(id, isBooked)
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
