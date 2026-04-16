package com.hamza.car;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CarListDataAccessService implements CarDAO {
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

    public void printCars(List<Car> list) {
        for (Car c : list) {
            System.out.println(c);
        }
    }

    @Override
    public List<Car> getAvailableCars() {
        return carList.stream()
                .filter(c -> !c.getBooked())
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getAvailableCarByType(CarType type) {
        return carList.stream()
                .filter(c -> !c.getBooked() && c.getType().equals(type))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findCarById(UUID id) {
        return carList.stream()
                .filter(c -> c.getId().equals(id))
                .findAny();
    }
}
