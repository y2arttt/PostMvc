import com.PostMvc.PostMvc.service.AuthService;
import com.PostMvc.PostMvc.Dao.UsersDao;
import com.PostMvc.PostMvc.domain.UsersVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsersDao usersDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() {
        // Given
        UsersVo inputUser = new UsersVo();
        inputUser.setUsername("admin");
        inputUser.setPassword("1212");

        UsersVo existingUser = new UsersVo();
        existingUser.setUsername("admin");
        existingUser.setPassword("encodedPassword");

        when(usersDao.read(any(UsersVo.class))).thenReturn(existingUser);
        when(passwordEncoder.matches(inputUser.getPassword(), existingUser.getPassword())).thenReturn(true);

        // When
        UsersVo result = authService.login(inputUser);

        // Then
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
        verify(usersDao).read(any(UsersVo.class));
        verify(passwordEncoder).matches(inputUser.getPassword(), existingUser.getPassword());
    }

    @Test
    void loginFailUserNotFound() {
        // Given
        UsersVo inputUser = new UsersVo();
        inputUser.setUsername("nonexistent");
        inputUser.setPassword("password");

        when(usersDao.read(any(UsersVo.class))).thenReturn(null);

        // When
        UsersVo result = authService.login(inputUser);

        // Then
        assertNull(result);
        verify(usersDao).read(any(UsersVo.class));
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void loginFailWrongPassword() {
        // Given
        UsersVo inputUser = new UsersVo();
        inputUser.setUsername("admin");
        inputUser.setPassword("wrongPassword");

        UsersVo existingUser = new UsersVo();
        existingUser.setUsername("admin");
        existingUser.setPassword("encodedPassword");

        when(usersDao.read(any(UsersVo.class))).thenReturn(existingUser);
        when(passwordEncoder.matches(inputUser.getPassword(), existingUser.getPassword())).thenReturn(false);

        // When
        UsersVo result = authService.login(inputUser);

        // Then
        assertNull(result);
        verify(usersDao).read(any(UsersVo.class));
        verify(passwordEncoder).matches(inputUser.getPassword(), existingUser.getPassword());
    }

    @Test
    void testPasswordEncoder() {
        // Given
        String rawPassword = "testPassword123";
        String encodedPassword = "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG";

        // When
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // Then
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
        assertEquals(encodedPassword, passwordEncoder.encode(rawPassword));
    }
}
