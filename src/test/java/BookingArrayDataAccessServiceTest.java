import com.hamza.booking.Booking;
import com.hamza.booking.BookingArrayDataAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookingArrayDataAccessServiceTest {
    private BookingArrayDataAccessService bookingDao;

    @BeforeEach
    void setUp() {
        bookingDao = new BookingArrayDataAccessService();
        bookingDao.clearAll();
    }

    private Booking createBooking(UUID bookingId, UUID userId, UUID carId) {
        return new Booking(
                bookingId,
                userId,
                carId,
                new BigDecimal("100.00"),
                LocalDateTime.of(2026, 4, 21, 10, 0),
                LocalDateTime.of(2026, 4, 23, 10, 0),
                LocalDateTime.of(2026, 4, 20, 10, 0)
        );
    }

    @Test
    void shouldSaveBooking() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = createBooking(bookingId, UUID.randomUUID(), UUID.randomUUID());

        boolean result = bookingDao.saveBooking(booking);

        assertTrue(result);
        assertEquals(1, bookingDao.getCurrentNumberOfBookings());
    }

    @Test
    void shouldReturnAllBookings() {
        Booking b1 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Booking b2 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);

        Booking[] result = bookingDao.getBookings();

        assertEquals(2, result.length);
        assertEquals(b1, result[0]);
        assertEquals(b2, result[1]);
    }

    @Test
    void shouldFindBookingById() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = createBooking(bookingId, UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(booking);

        Optional<Booking> result = bookingDao.findBookingById(bookingId);

        assertTrue(result.isPresent());
        assertEquals(booking, result.get());
    }

    @Test
    void shouldReturnEmptyWhenBookingNotFound() {
        Optional<Booking> result = bookingDao.findBookingById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnBookingsForGivenUser() {
        UUID userId = UUID.randomUUID();

        Booking b1 = createBooking(UUID.randomUUID(), userId, UUID.randomUUID());
        Booking b2 = createBooking(UUID.randomUUID(), userId, UUID.randomUUID());
        Booking b3 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);
        bookingDao.saveBooking(b3);

        Booking[] result = bookingDao.getUserBookings(userId);

        assertEquals(2, result.length);
        assertEquals(userId, result[0].getUserId());
        assertEquals(userId, result[1].getUserId());
    }

    @Test
    void shouldDeleteBooking() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = createBooking(bookingId, UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(booking);

        boolean deleted = bookingDao.deleteBooking(bookingId);

        assertTrue(deleted);
        assertEquals(0, bookingDao.getCurrentNumberOfBookings());
        assertTrue(bookingDao.findBookingById(bookingId).isEmpty());
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingBooking() {
        boolean result = bookingDao.deleteBooking(UUID.randomUUID());

        assertFalse(result);
    }

    @Test
    void shouldIncreaseCurrentNumberOfBookingsWhenSaving() {
        Booking b1 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Booking b2 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);

        assertEquals(2, bookingDao.getCurrentNumberOfBookings());
    }
}
