import com.hamza.car.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarDAO carDAO;

    @InjectMocks
    private CarService carService;

    @Test
    void shouldReturnCar() {
        //Given
        UUID id = UUID.randomUUID();
        Car car = new Car(id, CarMake.FIAT, CarType.GASOLINE, new BigDecimal(99), false);

        when(carDAO.findCarById(id)).thenReturn(Optional.of(car));
        // When
        Car result = carService.getCar(id);

        //Then
        assertNotNull(result);
        assertEquals(car, result);
        verify(carDAO).findCarById(id);
    }

    @Test
    void shouldThrowWhenCarNotFound() {
        //Given
        UUID id = UUID.randomUUID();
        when(carDAO.findCarById(id)).thenReturn(Optional.empty());
        // When

        //Then
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> carService.getCar(id)
        );
        assertTrue(exception.getMessage().contains(id.toString()));
        verify(carDAO).findCarById(id);
    }

    @Test
    void shouldReturnAvailableCars() {
        //Given
        List<Car> carList = List.of(
                new Car(UUID.randomUUID(), CarMake.FIAT, CarType.EV, new BigDecimal(55), false),
                new Car(UUID.randomUUID(), CarMake.BMW, CarType.GASOLINE, new BigDecimal(56), false),
                new Car(UUID.randomUUID(), CarMake.HONDA, CarType.EV, new BigDecimal(57), false),
                new Car(UUID.randomUUID(), CarMake.TOYOTA, CarType.GASOLINE, new BigDecimal(85), false),
                new Car(UUID.randomUUID(), CarMake.BMW, CarType.EV, new BigDecimal(95), false)
        );

        when(carDAO.getAvailableCars()).thenReturn(carList);
        // When
        List<Car> returnList = carService.getAvailableCars();

        //Then
        assertNotNull(returnList);
        assertEquals(carList, returnList);
        verify(carDAO).getAvailableCars();
    }

    @Test
    void shouldReturnAvailableCarsByType() {
        //Given
        List<Car> carList = List.of(
                new Car(UUID.randomUUID(), CarMake.FIAT, CarType.EV, new BigDecimal(55), false),
                new Car(UUID.randomUUID(), CarMake.HONDA, CarType.EV, new BigDecimal(57), false),
                new Car(UUID.randomUUID(), CarMake.BMW, CarType.EV, new BigDecimal(95), false)
        );
        CarType type = CarType.EV;
        when(carDAO.getAvailableCarByType(type))
                .thenReturn(carList);
        // When
        List<Car> result = carService.getAvailableCarsByType(type);
        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(carList, result);
        verify(carDAO).getAvailableCarByType(type);
    }
}
