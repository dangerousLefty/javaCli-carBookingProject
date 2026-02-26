package com.hamza.booking;

import com.hamza.car.Car;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class BookingDAO {

    private static Booking[] bookings;
    private static int maxBookings = 100;
    private static int currentNumberOfBookings = 0;

    static {
        bookings = new Booking[maxBookings];
    }

    //printing not responsibility of the DAO

    //used in bookingService
    public int getCurrentNumberOfBookings(){
        return currentNumberOfBookings;
    }

    //used in bookingService
    public Booking[] getBookings(){
        return bookings;
    }

    public boolean addBooking(Booking booking) {
        if (currentNumberOfBookings >= maxBookings){
            System.out.println("Expanding storage! Please wait!!");
            maxBookings *= 2;
            bookings = Arrays.copyOf(bookings, maxBookings);
        }

        Booking[] bookingList = bookings;
        for (int i = 0; i < bookingList.length; i++) {
            if (bookingList[i] == null) {
                bookings[i] = booking;
                currentNumberOfBookings++;
                return true;
            }
        }
        return false;
    }

    public Optional<Booking> getBookingById(UUID id){
        for (Booking b : bookings){
            //if (c.getId().equals(id) && !c.getBooked()){
            //if (Objects.equals(b.getId(), id)){
            //returning error on null Cannot invoke "com.hamza.booking.Booking.getUserId()" because "b" is null
            if (b != null && b.getId().equals(id)){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public Booking[] getUserBookings(UUID id) {
        int count = 0;
        Booking[] bookingList = bookings;
        Booking[] returnList = new Booking[maxBookings];
        for (Booking b : bookingList){
            //if (Objects.equals(b.getUserId(), id)){
            //returning error on null Cannot invoke "com.hamza.booking.Booking.getUserId()" because "b" is null
            if (b != null && b.getUserId().equals(id)){
                returnList[count] = b;
                count++;
            }
        }
        returnList = Arrays.copyOf(returnList, count);
        return returnList;
    }

    public boolean deleteBooking(UUID id) {

        Booking[] bookingList = bookings;
        for (int i = 0; i < bookingList.length; i++) {
            if (bookingList[i] != null && bookingList[i].getId().equals(id)) {
                bookingList[i] = null;
                currentNumberOfBookings--;
                return true;
            }
        }
        return false;
    }

}


