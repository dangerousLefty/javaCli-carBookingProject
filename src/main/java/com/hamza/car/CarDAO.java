package com.hamza.car;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CarDAO {
    private static final Car[] carList;

    static {
        carList = new Car[]{
                new Car(UUID.fromString("6d52898a-3e91-4e04-bf4e-427861c5873b"), CarMake.BMW, CarType.GASOLINE, new BigDecimal("89.00"),false),
                new Car(UUID.fromString("11ddcda2-be6b-4ea8-a4fe-fcee1917da96"), CarMake.FIAT, CarType.GASOLINE, new BigDecimal("74.22"), false),
                new Car(UUID.fromString("5883059b-2ecc-4928-847c-8bca1eb36e96"), CarMake.HONDA, CarType.GASOLINE, new BigDecimal("45.86"), false),
                new Car(UUID.fromString("9dfdce88-ee11-482d-9ce1-675ab83e752b"), CarMake.TOYOTA, CarType.EV, new BigDecimal("44.22"), false),
                new Car(UUID.fromString("6bc110f4-263e-413e-971b-f88c82858240"), CarMake.BMW, CarType.EV, new BigDecimal("76.45"), false),
        };
    }

    public Car[] getCars(){
        return carList;
    }

    public void printCars(Car[] list){
        for (Car c : list){
            System.out.println(c);
        }
    }

    public Car[] getAvailableCars(){
        int count = 0;

        for (Car c : carList){
            if (c != null && !c.getBooked()){
                count++;
            }
        }

        int unbookedCarCount = 0;
        Car[] returnList = new Car[count];
        for (int i = 0; i < carList.length && unbookedCarCount < count; i++){
            if (carList[i] != null && !carList[i].getBooked()){
                returnList[unbookedCarCount] = carList[i];
                unbookedCarCount++;
            }
        }

        return returnList;
    }

    public Car[] getAvailableCarByType(CarType type){
        int count = 0;

        for (Car c : carList){
            if (c != null && !c.getBooked() && c.getType().equals(type)){
                count++;
            }
        }

        int availableCarCount = 0;
        Car[] returnList = new Car[count];
        for (int i = 0; i < carList.length && availableCarCount < count; i++){
            if (carList[i] != null && !carList[i].getBooked() && carList[i].getType().equals(type)){
                returnList[availableCarCount] = carList[i];
                availableCarCount++;
            }
        }

        return returnList;
    }

    public Optional<Car> findCarById(UUID id){
        for (Car c : carList){
            if (c.getId().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }
}
