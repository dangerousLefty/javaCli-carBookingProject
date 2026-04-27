import com.hamza.booking.Booking;
import com.hamza.booking.BookingFileDataAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFileDataAccessServiceTest {
    @TempDir
    Path tempDir;

    private BookingFileDataAccessService bookingDao;
    private Path bookingFilePath;

    @BeforeEach
    void setUp() {
        bookingFilePath = tempDir.resolve("bookings.bin");
        bookingDao = new BookingFileDataAccessService(bookingFilePath.toString());
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
        //Given
        UUID bookingId = UUID.randomUUID();
        Booking booking = createBooking(bookingId, UUID.randomUUID(), UUID.randomUUID());

        //When
        bookingDao.saveBooking(booking);

        //Then
        List<Booking> bookings = bookingDao.getBookings();

        assertNotNull(bookings);
        assertEquals(1, bookings.size());
        assertEquals(bookingId, bookings.get(0).getBookingId());
    }

    @Test
    void shouldReturnAllBookings() {
        Booking b1 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Booking b2 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);

        List<Booking> result = bookingDao.getBookings();

        assertEquals(2, result.size());
        assertEquals(b1.getBookingId(), result.get(0).getBookingId());
        assertEquals(b2.getBookingId(), result.get(1).getBookingId());
    }

    @Test
    void shouldFindBookingById() {
        UUID targetId = UUID.randomUUID();

        Booking b1 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        Booking b2 = createBooking(targetId, UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);

        Optional<Booking> result = bookingDao.findBookingById(targetId);

        assertTrue(result.isPresent());
        assertEquals(targetId, result.get().getBookingId());
    }

    @Test
    void shouldReturnEmptyWhenBookingNotFound() {
        Booking booking = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        bookingDao.saveBooking(booking);

        Optional<Booking> result = bookingDao.findBookingById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnUserBookings() {
        UUID userId = UUID.randomUUID();

        Booking b1 = createBooking(UUID.randomUUID(), userId, UUID.randomUUID());
        Booking b2 = createBooking(UUID.randomUUID(), userId, UUID.randomUUID());
        Booking b3 = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(b1);
        bookingDao.saveBooking(b2);
        bookingDao.saveBooking(b3);

        List<Booking> result = bookingDao.getUserBookings(userId);

        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
    }

    @Test
    void shouldDeleteBooking() {
        UUID bookingId = UUID.randomUUID();
        Booking booking = createBooking(bookingId, UUID.randomUUID(), UUID.randomUUID());

        bookingDao.saveBooking(booking);

        boolean deleted = bookingDao.deleteBooking(bookingId);

        assertTrue(deleted);
        assertTrue(bookingDao.findBookingById(bookingId).isEmpty());
        assertEquals(0, bookingDao.getBookings().size());
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingBooking() {
        Booking booking = createBooking(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
        bookingDao.saveBooking(booking);

        boolean result = bookingDao.deleteBooking(UUID.randomUUID());

        assertFalse(result);
        assertEquals(1, bookingDao.getBookings().size());
    }

    @Test
    void shouldReturnEmptyListWhenNoBookingsExist() {
        List<Booking> result = bookingDao.getBookings();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
