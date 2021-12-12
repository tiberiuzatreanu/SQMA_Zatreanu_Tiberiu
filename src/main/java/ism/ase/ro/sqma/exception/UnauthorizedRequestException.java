package ism.ase.ro.sqma.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedRequestException extends GenericException {

    public UnauthorizedRequestException() {
        super(HttpStatus.FORBIDDEN);
    }
}
