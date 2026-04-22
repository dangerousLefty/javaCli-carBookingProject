import com.hamza.user.User;
import com.hamza.user.UserDAO;
import com.hamza.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers(){
        //Given
        List<User> users = List.of(
                new User(UUID.randomUUID(), "Larry"),
                new User(UUID.randomUUID(), "Elison")
        );
        //when
        when(userDAO.getUsers()).thenReturn(users);
        List<User> result = userService.getUsers();

        //then
        assertEquals(2, result.size());
        assertEquals(users, result);
        verify(userDAO).getUsers();
    }

    @Test
    void shouldReturnUserWhenUserExists() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Hamza");

        when(userDAO.findUserById(id)).thenReturn(Optional.of(user));

        User result = userService.getUser(id);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userDAO).findUserById(id);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFound() {
        //Given
        UUID id = UUID.randomUUID();
        when(userDAO.findUserById(id)).thenReturn(Optional.empty());
        // When
        //Then
        assertThrows(NoSuchElementException.class,
                () -> userService.getUser(id));
        verify(userDAO).findUserById(id);
    }
}
