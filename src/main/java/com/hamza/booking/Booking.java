package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.user.User;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {
    private UUID bookingId;
    //private UUID bookingUserId;
    private User bookingUser;
    private Car carBooked;
    private LocalDateTime bookingTime;

    public Booking(UUID bookingId, User bookingUser, Car carBooked, LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.bookingUser = bookingUser;
        this.carBooked = carBooked;
        this.bookingTime = bookingTime;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public User getBookingUser() {
        return bookingUser;
    }

    public void setBookingUser(User bookingUser) {
        this.bookingUser = bookingUser;
    }

    public Car getCarBooked() {
        return carBooked;
    }

    public void setCarBooked(Car carBooked) {
        this.carBooked = carBooked;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingUser=" + bookingUser +
                ", carBooked=" + carBooked +
                ", bookingTime=" + bookingTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId) && Objects.equals(bookingUser, booking.bookingUser) && Objects.equals(carBooked, booking.carBooked) && Objects.equals(bookingTime, booking.bookingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, bookingUser, carBooked, bookingTime);
    }
}
