package src.main.webmusicarchive.Application;

import org.springframework.http.HttpStatusCode;

public record ApplicationErrorMessage(String message, String errorCode) {
}
