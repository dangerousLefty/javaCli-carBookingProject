package com.hamza.car;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class Car {
    private UUID id;
    private CarMake make;
    private CarType type;
    private BigDecimal rentalRate;
    private boolean isBooked;

    public Car(UUID id, CarMake make, CarType type, BigDecimal rentalRate, boolean isBooked) {
        this.id = id;
        this.make = make;
        this.type = type;
        this.rentalRate = rentalRate;
        this.isBooked = isBooked;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CarMake getMake() {
        return make;
    }

    public void setMake(CarMake make) {
        this.make = make;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public boolean getBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Car{" +
                "id=" + id +
                ", make=" + make +
                ", type=" + type +
                ", rentalRate=" + rentalRate +
                ", isBooked=" + isBooked +
                '}';
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Car car = (Car) object;
        return isBooked == car.isBooked && java.util.Objects.equals(id, car.id) && java.util.Objects.equals(make, car.make) && java.util.Objects.equals(type, car.type) && java.util.Objects.equals(rentalRate, car.rentalRate);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id, make, type, rentalRate, isBooked);
    }
}
