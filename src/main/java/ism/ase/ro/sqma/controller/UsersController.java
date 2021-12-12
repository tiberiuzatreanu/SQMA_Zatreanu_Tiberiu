package ism.ase.ro.sqma.controller;

import ism.ase.ro.sqma.exception.GenericException;
import ism.ase.ro.sqma.exception.ServerException;
import ism.ase.ro.sqma.model.User;
import ism.ase.ro.sqma.service.UsersService;
import ism.ase.ro.sqma.transfer.LoginRequest;
import ism.ase.ro.sqma.transfer.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<String> handleException(@NotNull GenericException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @NotNull @Valid UserRequest userRequest)
            throws ServerException {
        usersService.addUser(userRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @NotNull @Valid LoginRequest loginRequest)
            throws GenericException {
        return ResponseEntity.ok(usersService.login(loginRequest));
    }


}
