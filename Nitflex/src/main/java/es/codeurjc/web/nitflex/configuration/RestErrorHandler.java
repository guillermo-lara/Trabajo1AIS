package es.codeurjc.web.nitflex.configuration;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.codeurjc.web.nitflex.service.exceptions.FilmNotFoundException;

@ControllerAdvice(basePackages = "es.codeurjc.web.nitflex.controller.rest")
public class RestErrorHandler {

    /**
     * When a 'FilmNotFound' exception occurs, the following method is executed
     * 
     * @param ex
     * @return a view with a message indicating the error
     */
    @ExceptionHandler({ FilmNotFoundException.class, IllegalArgumentException.class, BindException.class })
    public ResponseEntity<String> handleException(Exception ex) {
        return switch (ex) {
            case MethodArgumentNotValidException manvExp -> {
                // Check if getFieldError() is null before calling getDefaultMessage()
                String errorMessage = (manvExp.getFieldError() != null && manvExp.getFieldError().getDefaultMessage() != null)
                        ? manvExp.getFieldError().getDefaultMessage()
                        : "Validation error";
                yield ResponseEntity.badRequest().body(errorMessage);
            }
            case FilmNotFoundException fnfExp -> ResponseEntity.notFound().build();
            default -> ResponseEntity.badRequest().body(ex.getMessage());
        };
    }

}
