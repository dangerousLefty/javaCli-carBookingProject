import com.hamza.car.Car;
import com.hamza.car.CarArrayDataAccessService;
import com.hamza.car.CarMake;
import com.hamza.car.CarType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CarArrayDataAccessServiceTest {

    private CarArrayDataAccessService carDao;

    @BeforeEach
    void setUp() {
        carDao = new CarArrayDataAccessService();
        carDao.resetCars();
    }

    @Test
    void shouldReturnAllAvailableCars() {
        Car[] result = carDao.getAvailableCars();

        assertNotNull(result);
        assertEquals(5, result.length);
    }

    @Test
    void shouldReturnAvailableGasolineCars() {
        Car[] result = carDao.getAvailableCarByType(CarType.GASOLINE);

        assertNotNull(result);
        assertEquals(3, result.length);

        for (Car car : result) {
            assertEquals(CarType.GASOLINE, car.getType());
            assertFalse(car.getBooked());
        }
    }

    @Test
    void shouldReturnAvailableEvCars() {
        Car[] result = carDao.getAvailableCarByType(CarType.EV);

        assertNotNull(result);
        assertEquals(2, result.length);

        for (Car car : result) {
            assertEquals(CarType.EV, car.getType());
            assertFalse(car.getBooked());
        }
    }

    @Test
    void shouldNotReturnBookedCars() {
        UUID id = UUID.fromString("6d52898a-3e91-4e04-bf4e-427861c5873b");
        Car car = carDao.findCarById(id).orElseThrow();
        car.setBooked(true);

        Car[] result = carDao.getAvailableCars();

        assertEquals(4, result.length);

        for (Car c : result) {
            assertNotEquals(id, c.getId());
            assertFalse(c.getBooked());
        }
    }

    @Test
    void shouldFindCarById() {
        UUID id = UUID.fromString("9dfdce88-ee11-482d-9ce1-675ab83e752b");

        Optional<Car> result = carDao.findCarById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals(CarMake.TOYOTA, result.get().getMake());
        assertEquals(CarType.EV, result.get().getType());
    }

    @Test
    void shouldReturnEmptyWhenCarNotFound() {
        Optional<Car> result = carDao.findCarById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoAvailableCarsOfType() {
        Car[] evCars = carDao.getAvailableCarByType(CarType.EV);
        for (Car car : evCars) {
            car.setBooked(true);
        }

        Car[] result = carDao.getAvailableCarByType(CarType.EV);

        assertNotNull(result);
        assertEquals(0, result.length);
    }
}
