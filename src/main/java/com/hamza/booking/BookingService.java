package com.hamza.booking;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO){this.bookingDAO = bookingDAO;}

    public void addBooking(Booking booking){
        bookingDAO.addBooking(booking);
    }

    public boolean canAddBooking(){
        if (BookingDAO.getBookingSize() + 1 <= BookingDAO.getMaxBookings()){
            return true;
        }
        else {
            return false;
        }
    }

    /*
    public Booking[] getBookings(){
        return bookingDAO.getBookings();
    } */

    public void findBookingByUserId(String id){
        int count = 0;
        Booking[] bookingList = bookingDAO.getBookings();
        for (int ptr = 0; ptr < BookingDAO.getBookingSize(); ptr++){
            Booking b = bookingList[ptr];
            if (b.getBookingUser().getUserID().toString().equals(id)){
                System.out.println(b);
                count++;
            }
        }
        if (count == 0){
            System.out.println("❌ No bookings made with this user");
        }
    }

    public void getBookings(){
        int count = 0;
        Booking[] bookingList = bookingDAO.getBookings();
        for (int ptr = 0; ptr < BookingDAO.getBookingSize(); ptr++){
            Booking b = bookingList[ptr];
            System.out.println(b);
        }
    }

}
