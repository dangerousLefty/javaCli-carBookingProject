import com.hamza.booking.Booking;
import com.hamza.booking.BookingDAO;
import com.hamza.booking.BookingService;
import com.hamza.car.Car;
import com.hamza.car.CarMake;
import com.hamza.car.CarService;
import com.hamza.car.CarType;
import com.hamza.user.User;
import com.hamza.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingDAO bookingDAO;

    @Mock
    private CarService carService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldReturnBookingById() {
        //Given
        UUID bookingId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        LocalDateTime start = LocalDateTime.of(2024, 10, 01, 12,00);
        LocalDateTime end = LocalDateTime.of(2025, 10, 01, 12,00);
        Booking booking =
                new Booking(bookingId, userId, carId, new BigDecimal(66),
                        start, end, LocalDateTime.now());
        when(bookingDAO.findBookingById(bookingId)).thenReturn(Optional.of(booking));
        // When
        Booking result = bookingService.getBookingById(bookingId);
        //Then
        assertNotNull(result);
        assertEquals(booking, result);
        verify(bookingDAO).findBookingById(bookingId);
    }

    @Test
    void shouldNotReturnBookingById() {
        //Given
        UUID id = UUID.randomUUID();
        when(bookingDAO.findBookingById(id))
                .thenReturn(Optional.empty());
        // When
        //Then
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> bookingService.getBookingById(id)
        );
        assertTrue(exception.getMessage().contains(id.toString()));
        verify(bookingDAO).findBookingById(id);
    }

    @Test
    void shouldBookCarSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        User user = new User(userId, "Hamza");
        Car car = new Car(carId, CarMake.HONDA, CarType.EV, new BigDecimal("67"), false);

        LocalDateTime startDate = LocalDateTime.of(2026, 4, 21, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 4, 23, 10, 0);

        when(userService.getUser(userId)).thenReturn(user);
        when(carService.getCar(carId)).thenReturn(car);

        bookingService.bookCar(userId, carId, startDate, endDate, null);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingDAO).saveBooking(captor.capture());

        Booking saved = captor.getValue();

        assertEquals(userId, saved.getUserId());
        assertEquals(carId, saved.getCarId());
        assertEquals(startDate, saved.getStartDate());
        assertEquals(endDate, saved.getEndDate());
        assertEquals(new BigDecimal("201"), saved.getPrice());
    }

    @Test
    void shouldThrowWhenUserNotFoundDuringBooking() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        LocalDateTime startDate = LocalDateTime.of(2026, 4, 21, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 4, 23, 10, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        when(userService.getUser(userId))
                .thenThrow(new NoSuchElementException("❌ User not found with id " + userId));
        // When
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> bookingService.bookCar(userId, carId, startDate, endDate, formatter)
        );
        //Then
        assertTrue(exception.getMessage().contains(userId.toString()));
        verify(userService).getUser(userId);
        verifyNoInteractions(carService);
        verify(bookingDAO, never()).saveBooking(any());
    }

    @Test
    void shouldThrowWhenCarNotFoundDuringBooking() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        User user = new User(userId, "Hamza");

        LocalDateTime startDate = LocalDateTime.of(2026, 4, 21, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 4, 23, 10, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        when(userService.getUser(userId)).thenReturn(user);
        when(carService.getCar(carId))
                .thenThrow(new NoSuchElementException("❌ Car not found with id " + carId));

        // When + Then
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> bookingService.bookCar(userId, carId, startDate, endDate, formatter)
        );

        assertTrue(exception.getMessage().contains(carId.toString()));
        verify(userService).getUser(userId);
        verify(carService).getCar(carId);
        verify(bookingDAO, never()).saveBooking(any());
    }

    @Test
    void shouldThrowWhenCarAlreadyBooked() {
        //Given
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        User user = new User(userId, "Hamza");
        Car car = new Car(carId, CarMake.HONDA, CarType.GASOLINE, new BigDecimal("50.00"), true );

        LocalDateTime startDate = LocalDateTime.of(2026, 4, 21, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 4, 23, 10, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        when(userService.getUser(userId)).thenReturn(user);
        when(carService.getCar(carId)).thenReturn(car);
        // When
        // When + Then
        Exception exception = assertThrows(
                Exception.class,
                () -> bookingService.bookCar(userId, carId, startDate, endDate, formatter)
        );
        //Then
        assertTrue(exception.getMessage().contains("already rented out"));
        verify(userService).getUser(userId);
        verify(carService).getCar(carId);
        verify(bookingDAO, never()).saveBooking(any());
    }

    @Test
    void shouldThrowWhenStartDateIsAfterEndDate() {
        // Given
        UUID userId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        User user = new User(userId, "Hamza");
        Car car = new Car(carId, CarMake.HONDA, CarType.EV, new BigDecimal("50.00"), false);

        LocalDateTime startDate = LocalDateTime.of(2026, 4, 25, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2026, 4, 23, 10, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        when(userService.getUser(userId)).thenReturn(user);
        when(carService.getCar(carId)).thenReturn(car);

        // When + Then
        Exception exception = assertThrows(
                Exception.class,
                () -> bookingService.bookCar(userId, carId, startDate, endDate, formatter)
        );

        assertTrue(exception.getMessage().contains("Start date cannot be after End Date"));
        verify(userService).getUser(userId);
        verify(carService).getCar(carId);
        verify(bookingDAO, never()).saveBooking(any());
    }

    @Test
    void shouldDeleteBookingSuccessfully() {
        // Given
        UUID bookingId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();

        Booking booking = new Booking(
                bookingId,
                UUID.randomUUID(),
                carId,
                new BigDecimal("100.00"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now()
        );

        Car car = new Car(carId, CarMake.BMW, CarType.GASOLINE, new BigDecimal("50.00"), true);

        when(bookingDAO.findBookingById(bookingId)).thenReturn(Optional.of(booking));
        when(carService.getCar(carId)).thenReturn(car);
        when(bookingDAO.deleteBooking(bookingId)).thenReturn(true);

        // When
        boolean result = bookingService.deleteBooking(bookingId);

        // Then
        assertTrue(result);
        assertFalse(car.getBooked());

        verify(bookingDAO).findBookingById(bookingId);
        verify(carService).getCar(carId);
        verify(bookingDAO).deleteBooking(bookingId);
    }

    @Test
    void shouldReturnAllBookings() {
        // Given
        List<Booking> bookings = List.of(
                new Booking(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        new BigDecimal("100.00"),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now()
                ),
                new Booking(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        new BigDecimal("200.00"),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(2),
                        LocalDateTime.now()
                )
        );

        when(bookingDAO.getBookings()).thenReturn(bookings);

        // When
        List<Booking> result = bookingService.getBookings();

        // Then
        assertEquals(bookings, result);
        verify(bookingDAO).getBookings();
    }

    @Test
    void shouldReturnCurrentNumberOfBookings() {
        // Given
        List<Booking> bookings = List.of(
                mock(Booking.class),
                mock(Booking.class),
                mock(Booking.class)
        );

        when(bookingDAO.getBookings()).thenReturn(bookings);

        // When
        int result = bookingService.getCurrentNumberOfBookings();

        // Then
        assertEquals(3, result);
        verify(bookingDAO).getBookings();
    }

}
