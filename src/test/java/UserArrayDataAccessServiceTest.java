import com.hamza.user.User;
import com.hamza.user.UserArrayDataAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserArrayDataAccessServiceTest {

    private UserArrayDataAccessService userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserArrayDataAccessService();
    }

    @Test
    void shouldReturnAllUsers() {
        User[] result = userDao.getUsers();

        assertNotNull(result);
        assertEquals(10, result.length);
    }

    @Test
    void shouldFindUserById() {
        UUID id = UUID.fromString("f41c329c-a1ad-4a06-9fdc-695c7e9cb830");

        Optional<User> result = userDao.findUserById(id);

        assertTrue(result.isPresent());
        assertEquals("Montana", result.get().getName());
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        Optional<User> result = userDao.findUserById(UUID.randomUUID());

        assertTrue(result.isEmpty());
    }

}
