package com.hamza.booking;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class BookingArrayDataAccessService {

    private static Booking[] bookings;
    private static int maxBookings = 100;
    private static int currentNumberOfBookings = 0;

    static {
        bookings = new Booking[maxBookings];
    }

    public int getCurrentNumberOfBookings(){
        return currentNumberOfBookings;
    }

    public void clearAll() {
        maxBookings = 100;
        currentNumberOfBookings = 0;
        bookings = new Booking[maxBookings];
    }

    public Booking[] getBookings(){
        int count = 0;
        for (int i = 0; i < bookings.length; i++){
            if (bookings[i] != null){
                count++;
            }
        }
        Booking[] returnList = new Booking[count];

        int insertCount = 0;
        for (int i = 0; i < bookings.length; i++){
            if (bookings[i] != null){
                returnList[insertCount] = bookings[i];
                insertCount++;
            }
        }

        return returnList;
    }

    public boolean saveBooking(Booking booking) {
        if (currentNumberOfBookings >= maxBookings){
            System.out.println("Expanding storage! Please wait!!");
            maxBookings *= 2;
            bookings = Arrays.copyOf(bookings, maxBookings);
        }

        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i] == null) {
                bookings[i] = booking;
                currentNumberOfBookings++;
                return true;
            }
        }
        return false;
    }

    public Optional<Booking> findBookingById(UUID id){
        for (Booking b : bookings){
            if (b != null && b.getBookingId().equals(id)){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public Booking[] getUserBookings(UUID id) {
        int count = 0;
        for (Booking b : bookings){
            if (b != null && b.getUserId().equals(id)){
                count++;
            }
        }

        Booking[] returnList = new Booking[count];
        int booking = 0;
        for (int i = 0; i < bookings.length && booking < count; i++){
            if (bookings[i] != null && bookings[i].getUserId().equals(id)){
                returnList[booking] = bookings[i];
                booking++;
            }
        }
        return returnList;
    }

    public boolean deleteBooking(UUID id) {
        Booking[] bookingList = bookings;
        for (int i = 0; i < bookingList.length; i++) {
            if (bookingList[i] != null && bookingList[i].getBookingId().equals(id)) {
                bookingList[i] = null;
                currentNumberOfBookings--;
                return true;
            }
        }
        return false;
    }

}


