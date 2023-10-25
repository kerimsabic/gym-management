package ba.edu.ibu.gym.core.exceptions.auth;

import ba.edu.ibu.gym.core.exceptions.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends GeneralException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED.value());
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message);
    }
}