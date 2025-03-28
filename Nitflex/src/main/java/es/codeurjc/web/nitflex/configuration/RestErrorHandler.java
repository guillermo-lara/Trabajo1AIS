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
    public ResponseEntity<String> handleException(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException manvExp) {
            if (manvExp.getFieldError() != null) {
                return ResponseEntity.badRequest().body(manvExp.getFieldError().getDefaultMessage());
            }
            return ResponseEntity.badRequest().body("Invalid input data");
        }
        if (ex instanceof FilmNotFoundException) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
