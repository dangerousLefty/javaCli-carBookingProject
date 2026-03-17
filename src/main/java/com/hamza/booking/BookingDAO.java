package com.hamza.booking;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface BookingDAO {
    Booking[] getBookings();
    Booking[] getUserBookings(UUID bookingId);
    Optional<Booking> findBookingById(UUID bookingId);
    boolean saveBooking(Booking booking);
    boolean deleteBooking(UUID bookingId);
}
