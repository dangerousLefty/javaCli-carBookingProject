package com.hamza.booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingDAO {
    List<Booking> getBookings();
    List<Booking> getUserBookings(UUID bookingId);
    Optional<Booking> findBookingById(UUID bookingId);
    void saveBooking(Booking booking);
    boolean deleteBooking(UUID bookingId);
}
