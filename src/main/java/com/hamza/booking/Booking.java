package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {
    private UUID id;
    //private UUID userId;
    private User user;
    private Car car;
    private LocalDateTime time;


    public Booking(User user, Car car) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.car = car;
        this.time = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime gettime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking{"       + " \n" +
                " id=" + id     + ", \n" +
                " user=" + user + ", \n" +
                " car=" + car   + ", \n" +
                " time=" + time + ", \n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(user, booking.user) && Objects.equals(car, booking.car) && Objects.equals(time, booking.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, car, time);
    }
}
