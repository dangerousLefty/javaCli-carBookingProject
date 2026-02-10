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

    public void getAvailableCars(){
        for (Car c : getCars()){
            if (!c.getBooked()){
                System.out.println(c);
            }
        }
    }

    public void getAvailableEVcars(){
        for (Car c : getCars()){
            if (!c.getBooked() && c.getType().equals(CarType.EV)){
                System.out.println(c);
            }
        }
    }
}
