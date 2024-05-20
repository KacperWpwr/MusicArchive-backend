package src.main.webmusicarchive.Application;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ApplicationException extends RuntimeException{
    private final String errorMessage;
    private final String errorCode;
    private final HttpStatusCode statusCode;

    public ApplicationException(String errorCode, String errorMessage, HttpStatusCode statusCode){
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
        this.statusCode = statusCode;
    }
}
