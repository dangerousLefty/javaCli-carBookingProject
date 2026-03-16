package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.car.CarService;
import com.hamza.user.User;
import com.hamza.user.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO;
    private final UserService userService;
    private final CarService carService;

    public BookingService(BookingDAO bookingDAO, UserService userService, CarService carService) {
        this.bookingDAO = bookingDAO;
        this.userService = userService;
        this.carService = carService;
    }

    private BigDecimal calculatePrice(LocalDateTime startDate, LocalDateTime endDate, BigDecimal rentalRate){
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

    public boolean bookCar(UUID userId, UUID carId, LocalDateTime startDate, LocalDateTime endDate, DateTimeFormatter formatter) throws Exception {

        User user;
        Car car;

        try {
            user = userService.getUser(userId);
            car = carService.getCar(carId);
        }
        catch (NoSuchElementException e){
            throw new NoSuchElementException(e.getMessage());
        }

        if (car.getBooked()){
            throw new Exception("❌ Car is already rented out");
        }

        if (startDate.isAfter(endDate) || endDate.isBefore(startDate)){
            throw new Exception("❌ Start date cannot be after End Date");
        }

        BigDecimal finalPrice = calculatePrice(
                startDate, endDate, car.getRentalRate()
        );
        car.setBooked(true);
        UUID bookingId = UUID.randomUUID();
        LocalDateTime bookingTime = LocalDateTime.now();
        Booking newBooking = new Booking(
                bookingId,
                userId,
                car.getId(),
                finalPrice,
                startDate,
                endDate,
                bookingTime
        );

        return bookingDAO.addBooking(newBooking);
    }

    public boolean deleteBooking(UUID id){
        Booking b = getBookingById(id);
        Car c = carService.getCar(b.getCarId());
        c.setBooked(false);

        return bookingDAO.deleteBooking(id);
    }

    public Booking[] getUserBookings(UUID id){
        User u = userService.getUser(id);
        //^ this method checks if the user exists or not. (Is this correct way to implement?)
        return bookingDAO.getUserBookings(id);
    }

    public Booking[] getBookings(){
        return bookingDAO.getBookings();
    }

    public int getCurrentNumberOfBookings(){
        return bookingDAO.getCurrentNumberOfBookings();
    }

}
