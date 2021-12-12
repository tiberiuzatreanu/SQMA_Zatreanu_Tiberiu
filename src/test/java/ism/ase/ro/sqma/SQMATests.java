package ism.ase.ro.sqma;

import ism.ase.ro.sqma.exception.GenericException;
import ism.ase.ro.sqma.exception.NotFoundException;
import ism.ase.ro.sqma.exception.ServerException;
import ism.ase.ro.sqma.exception.UnauthorizedRequestException;
import ism.ase.ro.sqma.service.UsersService;
import ism.ase.ro.sqma.transfer.LoginRequest;
import ism.ase.ro.sqma.transfer.UserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SQMATests {

    private static final String CREATE_TAG = "CREATE";
    private static final String LOGIN_TAG = "LOGIN";

    private static final String NAME = "Zatreanu Tiberiu";
    private static final String USERNAME = "tiberiuzatreanu";
    private static final String PASSWORD = "sqma";

    private static final String USER_SHOULD_BE_PRESENT = "User should be present!";
    private static final String EXCEPTION_WHEN_NOT_FOUND = "Exception should be thrown when user is not found!";

    private static final String LOGIN_SUCCESSFUL = "Login should succeed!";
    private static final String EXCEPTION_WHEN_WRONG_PASSWORD = "Exception should be thrown when the password is not correct!";

    @InjectMocks
    private UsersService usersService;

    private static String scrambleString(@NotNull String initial) {
        return initial + UUID.randomUUID();
    }

    @BeforeEach
    public void addUser() throws ServerException {
        usersService.addUser(UserRequest.build(USERNAME, NAME, PASSWORD));
    }

    @Test
    @Tag(CREATE_TAG)
    void testUserIsCreatedSuccessfully() throws NotFoundException {
        assertThat(Optional.of(UsersService.findUser(USERNAME)))
                .as(USER_SHOULD_BE_PRESENT).isPresent();
    }

    @Test
    @Tag(CREATE_TAG)
    void testExceptionIsThrownWhenUserIsNotFound() {
        assertThrows(
                NotFoundException.class,
                () -> UsersService.findUser(scrambleString(USERNAME)),
                EXCEPTION_WHEN_NOT_FOUND
        );
    }

    @Test
    @Tag(LOGIN_TAG)
    void testLoginIsSuccessful() throws GenericException {
        assertThat(Optional.of(usersService.login(new LoginRequest(USERNAME, PASSWORD))))
                .as(LOGIN_SUCCESSFUL).isPresent();
    }

    @Test
    @Tag(LOGIN_TAG)
    void testLoginFails() {
        assertThrows(
                UnauthorizedRequestException.class,
                () -> usersService.login(new LoginRequest(USERNAME, scrambleString(PASSWORD))),
                EXCEPTION_WHEN_WRONG_PASSWORD
        );
    }

}
