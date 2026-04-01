package com.hamza.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CarListDataAccessService implements CarDAOLists{
    //private static final Car[] carList;
    private static final List<Car> carList;

    static {
        carList = new ArrayList<>(
                List.of(
                        new Car(UUID.fromString("6d52898a-3e91-4e04-bf4e-427861c5873b"), CarMake.BMW, CarType.GASOLINE, new BigDecimal("89.00"), false),
                        new Car(UUID.fromString("11ddcda2-be6b-4ea8-a4fe-fcee1917da96"), CarMake.FIAT, CarType.GASOLINE, new BigDecimal("74.22"), false),
                        new Car(UUID.fromString("5883059b-2ecc-4928-847c-8bca1eb36e96"), CarMake.HONDA, CarType.GASOLINE, new BigDecimal("45.86"), false),
                        new Car(UUID.fromString("9dfdce88-ee11-482d-9ce1-675ab83e752b"), CarMake.TOYOTA, CarType.EV, new BigDecimal("44.22"), false),
                        new Car(UUID.fromString("6bc110f4-263e-413e-971b-f88c82858240"), CarMake.BMW, CarType.EV, new BigDecimal("76.45"), false)
                )
        );
    }

    public void printCars(List<Car> list){
        for (Car c : list){
            System.out.println(c);
        }
    }

    @Override
    public List<Car> getAvailableCars(){
        List<Car> availableCars = new ArrayList<>();
        for (Car c : carList){
            if (!c.getBooked()){
                availableCars.add(c);
            }
        }

        return availableCars;
    }

    @Override
    public List<Car> getAvailableCarByType(CarType type){
        List<Car> availableCars = new ArrayList<>();

        for (Car c : carList){
            if (!c.getBooked() && c.getType().equals(type)){
                availableCars.add(c);
            }
        }
        return availableCars;
    }

    @Override
    public Optional<Car> findCarById(UUID id){
        for (Car c : carList){
            if (id.equals(c.getId())){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
