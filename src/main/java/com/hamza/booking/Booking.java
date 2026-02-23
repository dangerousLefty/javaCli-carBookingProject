package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {

    //private final BookingService bookingService = new BookingService();

    private UUID id;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private Car car;
    private LocalDateTime time;


    public Booking(User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", car=" + car +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return
                Objects.equals(id, booking.id) &&
                        Objects.equals(user, booking.user) &&
                        Objects.equals(startDate, booking.startDate) &&
                        Objects.equals(endDate, booking.endDate) &&
                        Objects.equals(price, booking.price) &&
                        Objects.equals(car, booking.car) &&
                        Objects.equals(time, booking.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, startDate, endDate, price, car, time);
    }

}
