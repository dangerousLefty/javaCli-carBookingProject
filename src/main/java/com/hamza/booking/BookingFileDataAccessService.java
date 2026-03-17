package com.hamza.booking;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class BookingFileDataAccessService implements BookingDAO {

    private static final String bookingFile = "src/main/java/com/hamza/bookings.bin";

    static {
        File file = new File(bookingFile);

        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public Booking[] getBookings(){
        File file = new File(bookingFile);
        if (!file.exists()) {
            return new Booking[0]; // empty array if no bookings yet
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Booking[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }
    }

    @Override
    public Booking[] getUserBookings(UUID userId) {
        File file = new File(bookingFile);
        Booking[] bookings = getBookings();
        int count = 0;
        for (Booking b : bookings){
            if (b != null && b.getUserId().equals(userId)){
                count++;
            }
        }
        Booking[] returnList = new Booking[count];
        int booking = 0;
        for (int i = 0; i < bookings.length && booking < count; i++){
            if (bookings[i] != null && bookings[i].getUserId().equals(userId)){
                returnList[booking] = bookings[i];
                booking++;
            }
        }

        return returnList;
    }

    @Override
    public Optional<Booking> findBookingById(UUID bookingId) {
        try(
                FileInputStream fileIn = new FileInputStream(bookingFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
        ) {
            Booking[] bookings = (Booking[]) in.readObject();

            for (Booking b : bookings){
                if (b != null && b.getBookingId().equals(bookingId)){
                    return Optional.of(b);
                }
            }
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException("Failed to read bookings file", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean saveBooking(Booking booking){
        Booking[] bookings = null;
        File file = new File(bookingFile);

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error creating bookings file" ,e);
            }
        }

        // Step 1: read existing bookings if file exists
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
                bookings = (Booking[]) in.readObject();
            }
            catch (EOFException e){
                bookings = new Booking[0];
            }
            catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("Failed to read bookings file", e);
            }

        // Step 2: create new array with extra slot
        Booking[] updatedBookings = Arrays.copyOf(bookings, bookings.length + 1);
        updatedBookings[bookings.length] = booking;

        // Step 3: write the updated array back
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(updatedBookings);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write bookings file", e);
        }
        return true;
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {
        File file = new File(bookingFile);
        Booking[] bookings = getBookings();
        int index = -1;

        for (int i = 0; i < bookings.length; i++) {
            if (bookings[i].getBookingId().equals(bookingId)) {
                index = i;
                break;
            }
        }
        if (index == -1) {return false;}

        //create array without deleted booking
        Booking[] updated = new Booking[bookings.length - 1];
        for (int i = 0, j = 0; i < bookings.length; i++){
            if (i == index){
                continue;
            }
            updated[j] = bookings[i];
            j++;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(updated);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write bookings file", e);
        }
        return true;

    }
}
