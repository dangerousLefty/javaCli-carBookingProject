package com.hamza.booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingFileDataAccessService implements BookingDAO {

    private static final String bookingFilePath = "src/main/java/com/hamza/bookings.bin";
    private static final File bookingFile = new File(bookingFilePath);

    static {
        if (bookingFile.exists()) {
            bookingFile.delete();
        }
    }

    @Override
    public boolean saveBooking(Booking booking) {
        if (!bookingFile.exists()) {
            try {
                bookingFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error creating bookings file", e);
            }
        }

        boolean append = bookingFile.length() > 0;

        try (ObjectOutputStream out = append
                ? new AppendableObjectOutputStream(new FileOutputStream(bookingFile, true))
                : new ObjectOutputStream(new FileOutputStream(bookingFile))) {

            out.writeObject(booking);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save booking", e);
        }
    }

    private List<Booking> readAllBookings() {
        if (!bookingFile.exists() || bookingFile.length() == 0) {
            return new ArrayList<>();
        }

        List<Booking> bookings = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            while (true) {
                try {
                    Booking booking = (Booking) in.readObject();
                    if (booking != null) {
                        bookings.add(booking);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }

        return bookings;
    }

    @Override
    public Optional<Booking> findBookingById(UUID bookingId) {
        return readAllBookings().stream()
                //.filter(b -> bookingId.equals(b.getBookingId()))
                .findFirst();
    }

    @Override
    public List<Booking> getBookings() {
        return readAllBookings();
    }

    @Override
    public List<Booking> getUserBookings(UUID userId) {
        return readAllBookings().stream()
                .filter(b -> userId.equals(b.getUserId()))
                .toList();
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {
        if (!bookingFile.exists() || bookingFile.length() == 0) {
            return false;
        }

        File tempFile = new File("src/main/java/com/hamza/temp.bin");
        boolean deleted = false;

        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile));
             ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempFile))
        ) {
            while (true) {
                try {
                    Booking booking = (Booking) in.readObject();

                    if (booking != null && booking.getBookingId().equals(bookingId)) {
                        deleted = true;
                        continue;
                    }

                    out.writeObject(booking);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error deleting booking", e);
        }

        if (!deleted) {
            tempFile.delete();
            return false;
        }

        if (!bookingFile.delete()) {
            throw new RuntimeException("Failed to delete original booking file");
        }

        if (!tempFile.renameTo(bookingFile)) {
            throw new RuntimeException("Failed to rename temp file");
        }

        return true;
    }

    //this class will be used to append to bin file without adding a new header
    class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset(); // prevents header duplication
        }
    }
}
