package ism.ase.ro.sqma.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class GenericException extends Exception {

    private final HttpStatus status;

}
