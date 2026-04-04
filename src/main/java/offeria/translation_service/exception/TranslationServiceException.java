package offeria.translation_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base custom exception for the translation service.
 */
@Getter
public class TranslationServiceException extends RuntimeException {
    private final HttpStatus status;

    public TranslationServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
