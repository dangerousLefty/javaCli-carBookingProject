package com.hamza.booking;

import java.util.Arrays;
import java.util.UUID;

public class BookingDAO {

    //the DAO objects are responsible of retrieving
    //data from the database. doesn't think about
    //whether string passed to it is valid or not
    //since we keep changing the size of the array,
    // we cant make this variable to be final
    private static Booking[] bookings;

    //booking Array starts with 1,
    //then increments every time it gets full
    //this will be the size of the Booking array
    private static int maxBookings = 1;

    //how many bookings are currently in the Array
    private static int numOfBookings = 0;

    static {
        bookings = new Booking[maxBookings];
    }

    public Booking[] getBookings(){
        return bookings;
    }

    public void printBookings(Booking[] list){
        for (Booking b : list){
            System.out.println(b);
        }
    }

    public int getNumOfBookings(){
        return numOfBookings;
    }

    public void decrementBookings(){
        numOfBookings--;
    }

    public int getMaxBookings(){
        return maxBookings;
    }

    public boolean canAddBooking(){
        return getNumOfBookings() < getMaxBookings();
    }

    private void expandArray(){
        maxBookings *= 2;
        bookings = Arrays.copyOf(bookings, maxBookings);
        System.out.println("Expanding storage! Please wait!!");
    }

    public boolean addBooking(Booking booking){
        if (!canAddBooking()){
            expandArray();
        }

        Booking[] bookingList = getBookings();
        for (int i = 0; i < bookingList.length; i++){

            if (bookingList[i] == null){
                bookings[i] = booking;
                break;
            }
        }
        numOfBookings++;
        return true;
    }


    public Booking[] getUserBookings(UUID id){
        int count = 0;
        Booking[] bookingList = getBookings();
        Booking[] returnList = new Booking[getMaxBookings()];

            for (int ptr = 0; ptr < getNumOfBookings(); ptr++){
                //Booking b = bookingList[ptr];

                if (bookingList[ptr] == null){
                    continue;
                }

                else if (bookingList[ptr].getUser().getUserID().equals(id)){
                    returnList[count] = bookingList[ptr];
                    count++;
                }
            }

                returnList = Arrays.copyOf(returnList, count);

        return returnList;
    }

    public boolean deleteBooking(UUID id){
        Booking[] bookingList = getBookings();
        for (int i = 0; i < bookingList.length; i++){
            if (bookingList[i] != null && bookingList[i].getId().equals(id)){
                //BookingDAO.decrementBookings();
                decrementBookings();
                bookingList[i].getCar().setBooked(false);
                bookingList[i] = null;
                return true;
            }
        }
        return false;
    }

}


