package ism.ase.ro.sqma.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GenericException {

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
