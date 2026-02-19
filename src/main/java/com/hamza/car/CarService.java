package com.hamza.car;

import java.util.NoSuchElementException;
import java.util.UUID;

public class CarService {
    private final CarDAO carDAO = new CarDAO();

    public Car getCar(UUID id){
        return carDAO.findCarById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ Car not found with id ".concat(id.toString())
                ));
    }

    public Car[] getCars(){
        return carDAO.getCars();
    }

    public Car[] getAvailableCars(int i){
        return carDAO.getAvailableCars(i);
    }

    public Car[] getAvailableEvCars(){
        return carDAO.getAvailableEvCars();
    }

    public void printCars(Car[] list){
        carDAO.printCars(list);
    }
}
