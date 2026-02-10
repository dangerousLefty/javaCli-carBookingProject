package com.hamza.booking;

import com.hamza.car.Car;
import com.hamza.car.CarService;

import java.util.UUID;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();

    public void addBooking(Booking booking){

        if (!canAddBooking()){
            BookingDAO.expandArray();
            System.out.println("Expanding Array! One moment please");
        }
//            car.setBooked(true);
            booking.getCar().setBooked(true);

            Booking[] bookingList = bookingDAO.getBookings();
            for (int i = 0; i < bookingList.length; i++){
                if (bookingList[i] == null){
                    bookingDAO.addBooking(booking, i);
                }
            }

            System.out.println("✅ Booking is created successfully!");
            System.out.println("Booking id: " + booking.getId().toString());
    }

    public boolean canAddBooking(){
        if (BookingDAO.getNumOfBookings() < BookingDAO.getMaxBookings()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteBooking(UUID id){
        Booking[] bookingList = bookingDAO.getBookings();
        for (int i = 0; i < bookingList.length; i++){
            if (bookingList[i] != null && bookingList[i].getId().equals(id)){
                BookingDAO.decrementBookings();
                System.out.println("✅ Booking deleted successfully");
                bookingList[i] = null;
                return true;
            }
        }
        System.out.println("❌ Booking does not exist");
        return false;
    }

    public Booking[] findBookingByUserId(UUID id){
        int count = 0;
        Booking[] bookingList = bookingDAO.getBookings();
        Booking[] returnList = new Booking[bookingList.length];
        if (bookingList[0] == null){
            System.out.println("❌ No bookings found");
        }
        else {
            for (int ptr = 0; ptr < BookingDAO.getNumOfBookings(); ptr++){
                Booking b = bookingList[ptr];
                if (b.equals(null)){
                    continue;
                }

                //if (b.getUser().getUserID().toString().equals(id)){
                else if (b.getUser().getUserID().equals(id)){
                    //System.out.println(b);
                    //we should return a data structure containing the bookings
                    //rather than the bookings printed. First calculate how many hits we get

                    count++;
                }
            }


            if (count == 0){
                System.out.println("❌ No bookings made with this user");
                returnList = new Booking[0];
            }
            else {
                returnList = new Booking[count];
                int insertPtr = 0;

                for (int ptr = 0; ptr < count; ptr++){
                    Booking b = bookingList[ptr];
                    if (b.equals(null)){
                        continue;
                    }

                    //if (b.getUser().getUserID().toString().equals(id)){
                    else if (b.getUser().getUserID().equals(id)){
                        System.out.println(b);
                        returnList[insertPtr] = b;
                        insertPtr++;

                    }
                }

                return returnList;
            }
        }
        return returnList;
    }

    public Booking[] getBookings(){
        int count = 0;
        Booking[] bookingList = bookingDAO.getBookings();

        /*
        for (int ptr = 0; ptr < BookingDAO.getNumOfBookings(); ptr++){
            Booking b = bookingList[ptr];
            System.out.println(b);
        }
        */
        return bookingList;
    }

    public int getNumOfBookings(){
        return BookingDAO.getNumOfBookings();
    }

}
