package lt.techin.schedule.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(
            HttpServletRequest request, ConstraintViolationException constraintViolationException) {
        logger.error("constraintViolationException: {}, cause: {}",
                constraintViolationException.getMessage(), constraintViolationException.getCause());
        var errorStatus = HttpStatus.BAD_REQUEST;
        var errorDto = new ErrorDto(
                request.getRequestURL().toString(),
                "Sukurti nepavyko. Neužpildyti privalomi laukai. ",
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDto> handleSubjectValidationException(HttpServletRequest request,
                                                                     ValidationException validationException) {
        logger.error("validationException: {}, for field: {}", validationException.getMessage(),
                validationException.getField());
        var errorStatus = HttpStatus.BAD_REQUEST;
        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                validationException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(TeacherException.class)
    public ResponseEntity<ErrorDto> handleTeacherException(HttpServletRequest request,
                                                           TeacherException teacherException) {
        logger.error("TeacherException: {}, for field: {}",
                teacherException.getMessage(),
                teacherException.getCause());
        var errorStatus = teacherException.getStatus();
        var errorDto = new ErrorDto(
                request.getRequestURL().toString(),
                teacherException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }
}
