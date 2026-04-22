package com.hamza.car;

import com.github.javafaker.Faker;
import com.github.javafaker.Pokemon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class CarFakerDataAccessService implements CarDAO{

    private static final List<Car> carList = new ArrayList<>();

    static {
        Faker faker = new Faker();
        Pokemon pokemon = faker.pokemon();
        Random random = new Random();
        CarMake[] carMakes = CarMake.values();
        for (int i = 0; i < 10; i++){
            boolean flag = random.nextBoolean();
            CarType typePicker = flag ? CarType.GASOLINE : CarType.EV;
            carList.add(
                    new Car(UUID.randomUUID(),
                    carMakes[random.nextInt(carMakes.length)],
                    typePicker,
                    new BigDecimal(random.nextDouble(30, 99)).setScale(2, RoundingMode.HALF_UP),
                    false));
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
                .filter(c -> type.equals(c.getType()) && !c.getBooked())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findCarById(UUID carId) {
        return carList.stream()
                //.filter(c -> c.getId().equals(id))
                .filter(c -> carId.equals(c.getId()))
                .findFirst();
    }
}
