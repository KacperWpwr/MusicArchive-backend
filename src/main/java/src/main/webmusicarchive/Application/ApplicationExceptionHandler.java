package src.main.webmusicarchive.Application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<ApplicationErrorMessage>
            handleApplicationException(ApplicationException exception){
        return new ResponseEntity<>(
                new ApplicationErrorMessage(exception.getErrorMessage(), exception.getErrorCode()),
                exception.getStatusCode()
        );
    }
}
