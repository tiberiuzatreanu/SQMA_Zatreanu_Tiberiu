package ism.ase.ro.sqma.service;

import ism.ase.ro.sqma.exception.GenericException;
import ism.ase.ro.sqma.exception.NotFoundException;
import ism.ase.ro.sqma.exception.ServerException;
import ism.ase.ro.sqma.exception.UnauthorizedRequestException;
import ism.ase.ro.sqma.model.User;
import ism.ase.ro.sqma.model.UserWithPassword;
import ism.ase.ro.sqma.transfer.LoginRequest;
import ism.ase.ro.sqma.transfer.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UsersService {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String HASH_ERROR = "Failed to compute the hash";

    private static final Set<UserWithPassword> USERS = new HashSet<>();

    private static Optional<String> hashPassword(@NotNull String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            return Optional.ofNullable(digest.digest(password.getBytes(StandardCharsets.UTF_8))).map(String::new);
        } catch (Exception e) {
            log.error(HASH_ERROR, e);
            return Optional.empty();
        }
    }

    private static String hashPasswordStrict(@NotNull String password) throws ServerException {
        return hashPassword(password).orElseThrow(ServerException::new);
    }

    public static UserWithPassword findUser(@NotNull String username) throws NotFoundException {
        return USERS.stream().filter(user -> username.equals(user.getUsername())).findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public void addUser(@NotNull @Valid UserRequest userRequest) throws ServerException {
        USERS.add(UserWithPassword.build(userRequest, hashPasswordStrict(userRequest.getPassword())));
    }

    public User login(@NotNull @Valid LoginRequest loginRequest) throws GenericException {
        String receivedHash = hashPasswordStrict(loginRequest.getPassword());
        return Optional.of(findUser(loginRequest.getUsername()))
                .filter(user -> receivedHash.equals(user.getPasswordHash()))
                .orElseThrow(UnauthorizedRequestException::new);
    }

}
