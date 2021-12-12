package ism.ase.ro.sqma.exception;

import org.springframework.http.HttpStatus;

public class ServerException extends GenericException {

    public ServerException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
