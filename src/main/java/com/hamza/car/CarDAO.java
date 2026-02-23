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
        Car[] carList = getCars();
        Car[] availableCarList = new Car[getCars().length];

        if (carList[0] == null){
            System.out.println("❌ No cars found");
            return new Car[0];
        }
        else {
            for (int ptr = 0; ptr < carList.length; ptr++){
                Car c = carList[ptr];
                    if (!c.getBooked()){
                        availableCarList[count] = c;
                        count++;
                    }
            }
        }
        availableCarList = Arrays.copyOf(availableCarList, count);
        return availableCarList;
    }

    public Car[] getAvailableCarByType(CarType type){
        int count = 0;
        Car[] carList = getCars();
        Car[] availableCarList = new Car[getCars().length];

        if (carList[0] == null){
            System.out.println("❌ No cars found");
            return new Car[0];
        }
        else {
            for (int ptr = 0; ptr < carList.length; ptr++){
                Car c = carList[ptr];

                if (!c.getBooked() && c.getType().equals(type)){
                    availableCarList[count] = c;
                    count++;
                }
            }
        }
        availableCarList = Arrays.copyOf(availableCarList, count);
        return availableCarList;
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
