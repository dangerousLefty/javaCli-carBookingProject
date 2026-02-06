package com.hamza.car;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Car {
    private UUID carId;
    private CarMake carMake;
    private CarType carType;
    private BigDecimal rentalRate;
    private boolean isBooked;

    public Car(UUID carId, CarMake carMake, CarType carType, BigDecimal rentalRate, boolean isBooked) {
        this.carId = carId;
        this.carMake = carMake;
        this.carType = carType;
        this.rentalRate = rentalRate;
        this.isBooked = isBooked;
    }

    public UUID getCarId() {
        return carId;
    }

    public String getCarIdString(){
        return carId.toString();
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public CarMake getCarMake() {
        return carMake;
    }

    public void setCarMake(CarMake carMake) {
        this.carMake = carMake;
    }

    public CarType getCarType() {
        return carType;
    }

    public boolean getBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carMake=" + carMake +
                ", carType=" + carType +
                ", rentalRate=" + rentalRate.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId) && carMake == car.carMake && carType == car.carType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId, carMake, carType);
    }
}
