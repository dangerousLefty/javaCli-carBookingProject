package com.hamza.booking;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

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
        boolean append = bookingFile.exists() && bookingFile.length() > 0;

        try (ObjectOutputStream out = append
                ? new AppendableObjectOutputStream(new FileOutputStream(bookingFile, true))
                : new ObjectOutputStream(new FileOutputStream(bookingFile))) {

            out.writeObject(booking);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save booking", e);
        }
    }

    @Override
    public Optional<Booking> findBookingById(UUID bookingId) {
        if (!bookingFile.exists() || bookingFile.length() == 0) {
            return Optional.empty();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            while (true) {
                try {
                    Booking b = (Booking) in.readObject();
                    if (b != null && b.getBookingId().equals(bookingId)) {
                        return Optional.of(b);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }
        return Optional.empty();
    }

    @Override
    public Booking[] getBookings() {
        int count = 0;
        Booking[] bookings;
        if (!bookingFile.exists() || bookingFile.length() < 1) {
            return new Booking[0];
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            while (true) {
                try {
                    Booking b = (Booking) in.readObject();
                    if (b != null) {
                        count++;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            int index = 0;
            bookings = new Booking[count];
            while (index < count) {
                try {
                    Booking b = (Booking) in.readObject();
                    bookings[index] = b;
                    index++;
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
    public Booking[] getUserBookings(UUID userId) {
        int count = 0;
        Booking[] userBookings;
        if (!bookingFile.exists() || bookingFile.length() < 1) {
            return new Booking[0];
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            while (true) {
                try {
                    Booking b = (Booking) in.readObject();
                    if (b != null && b.getUserId().equals(userId)) {
                        count++;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile))) {
            int index = 0;
            userBookings = new Booking[count];
            while (index < count) {
                try {
                    Booking b = (Booking) in.readObject();
                    if (b != null && b.getUserId().equals(userId)) {
                        userBookings[index] = b;
                        index++;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to read bookings file", e);
        }
        return userBookings;
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {
        if (!bookingFile.exists() || bookingFile.length() < 1) {
            return false;
        }

        Optional<Booking> bookingToDelete = findBookingById(bookingId);
        if (bookingToDelete.isEmpty()) {
            return false;
        }

        File tempFile = new File("src/main/java/com/hamza/temp.bin");
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(bookingFile));
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempFile))
        ) {
            while (true) {
                try {
                    Booking b = (Booking) in.readObject();
                    if (b.getBookingId().equals(bookingId)) {
                        continue;
                    }
                    out.writeObject(b);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error deleting booking", e);
        }

        bookingFile.delete();
        tempFile.renameTo(bookingFile);

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
