package com.hamza.booking;

public class BookingDAO {

    //the DAO objects are responsible of retrieving
    //data from the database. doesn't think about
    //whether string passed to it is valid or not
    private static final Booking[] bookings;
    private static final int maxBookings = 50;
    private static int bookingSize = 0;

    static {
        bookings = new Booking[maxBookings];
    }

    public Booking[] getBookings(){
        return bookings;
    }

    public static int getBookingSize() {
        return bookingSize;
    }

    public static int getMaxBookings(){
        return maxBookings;
    }

    public void addBooking(Booking booking){
        bookings[bookingSize] = booking;
        bookingSize++;
    }

}


