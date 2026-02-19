package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.car.CarService;
import com.hamza.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public UUID generateUserId(){
        return UUID.randomUUID();
    }

    public LocalDateTime returnBookingTime(){
        return LocalDateTime.now();
    }

    public BigDecimal calculatePrice(LocalDate startDate, LocalDate endDate, BigDecimal rentalRate){
        long daysCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //we do +1 because we calculate the days inclusive of start & end date
        BigDecimal rentalPrice = new BigDecimal(daysCount).multiply(rentalRate);

        return rentalPrice;
    }

    public boolean addBooking(Booking booking){
            booking.getCar().setBooked(true);
            return bookingDAO.addBooking(booking);
    }

    public boolean deleteBooking(UUID id){
        return bookingDAO.deleteBooking(id);
    }

    public Booking[] getUserBookings(UUID id){
        Booking[] list = bookingDAO.getUserBookings(id);
        bookingDAO.printBookings(list);

        return list;
    }

    public Booking[] getBookings(){
        Booking[] list = null;
        if (bookingDAO.getNumOfBookings() == 0) {
            System.out.println("❌ No bookings found");
            return new Booking[0];
        }
        else {
            list = bookingDAO.getBookings();
        }
        bookingDAO.printBookings(list);
        return list;
    }

    public int getNumOfBookings(){
        return bookingDAO.getNumOfBookings();
    }

}
