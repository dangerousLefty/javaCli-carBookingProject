package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.car.CarService;
import com.hamza.user.User;
import com.hamza.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final UserService userService = new UserService();
    private final CarService carService = new CarService();

//    public UUID generateUserId(){
//        return UUID.randomUUID();
//    }

//    public LocalDateTime returnBookingTime(){
//        return LocalDateTime.now();
//    }

    public BigDecimal calculatePrice(LocalDate startDate, LocalDate endDate, BigDecimal rentalRate){
        long daysCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        //we do +1 because we calculate the days inclusive of start & end date
        BigDecimal rentalPrice = new BigDecimal(daysCount).multiply(rentalRate);

        return rentalPrice;
    }

    public Booking getBookingById(UUID id){
        return bookingDAO.getBookingById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ Booking not found with given id"
                ));
    }

    public boolean bookCar(UUID userId, Car car, BigDecimal price, LocalDate from, LocalDate to){
        //TODO: is 'from' date less than 'to' date? (Taken care of in DateInput class)
        //TODO: check if user exists (Taken care of in userService)
        //TODO: check if car exists (Taken care of in carService)
        //TODO: check if car not being rented (Taken care of in Car DAO)
        car.setBooked(true);
        Booking newBooking = new Booking(
            userId, car.getId(), price, from, to
        );

        return bookingDAO.addBooking(newBooking);
    }

    public boolean deleteBooking(UUID id){
        //TODO: does the booking exist?
        Booking b = getBookingById(id);
        //TODO: find the car associated with the booking and set it status to false
        Car c = carService.getCar(b.getCarId(), true);
        c.setBooked(false);

        return bookingDAO.deleteBooking(id);
    }

    public Booking[] getUserBookings(UUID id){
        //TODO: does the user exist?
        //in order to implement the check ^, we need to use UserService class
        User u = userService.getUser(id);
        return bookingDAO.getUserBookings(id);
    }

    public int numOfBookings(){
        return bookingDAO.getCurrentNumberOfBookings();
    }

    public Booking[] getBookings(){
        Booking[] list = null;
        if (getCurrentNumberOfBookings() == 0) {
            return new Booking[0];
        }
        else {
            list = bookingDAO.getBookings();
        }
        return list;
    }

    public int getCurrentNumberOfBookings(){
        return bookingDAO.getCurrentNumberOfBookings();
    }

}
