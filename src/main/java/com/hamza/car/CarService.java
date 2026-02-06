package com.hamza.car;

import java.util.NoSuchElementException;

public class CarService {
    private CarDAO carDAO;

    public CarService(CarDAO carDAO){this.carDAO = carDAO;}

    public Car getCar(String id){
        return carDAO.findCarById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ Car not found with id ".concat(id)
                ));
    }

    public Car[] getCars(){
        return carDAO.getCars();
    }

    public void getCarList(){
        for (Car c : getCars()){
            if (!c.getBooked()){
                System.out.println(c);
            }
        }
    }

    public void getEvCarList(){
        for (Car c : getCars()){
            if (!c.getBooked() && c.getCarType().equals(CarType.EV)){
                System.out.println(c);
            }
        }
    }
}
