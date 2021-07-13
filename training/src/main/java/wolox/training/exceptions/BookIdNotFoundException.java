package wolox.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Book id not found")
public class BookIdNotFoundException extends RuntimeException {

    public BookIdNotFoundException() {
        super();
    }
}
