package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Booking {

    private UUID id;
    private UUID userId;
    private UUID carId;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;


    public Booking(
            UUID userId,
            UUID carId,
            BigDecimal price,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", userId=" + userId +
                ", carId=" + carId +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) &&
                Objects.equals(userId, booking.userId) &&
                Objects.equals(carId, booking.carId) &&
                Objects.equals(price, booking.price) &&
                Objects.equals(startDate, booking.startDate) &&
                Objects.equals(endDate, booking.endDate) &&
                Objects.equals(createdAt, booking.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, carId, price, startDate, endDate, createdAt);
    }
}
