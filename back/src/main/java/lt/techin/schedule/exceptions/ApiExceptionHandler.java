package lt.techin.schedule.exceptions;

import jakarta.servlet.http.HttpServletRequest;
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

    @ExceptionHandler(SubjectValidationException.class)
    public ResponseEntity<ErrorDto> handleSubjectValidationException(HttpServletRequest request, SubjectValidationException subjectValidationException) {
        logger.error("subjectValidationException: {}, for field: {}", subjectValidationException.getMessage(), subjectValidationException.getField());

        var errorStatus = HttpStatus.BAD_REQUEST;

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                subjectValidationException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
                request.getRequestURL().toString(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(TeacherException.class)
    public ResponseEntity<ErrorDto> handleTeacherException(HttpServletRequest request, TeacherException teacherException) {
        logger.error("TeacherException: {}, for field: {}",
                teacherException.getMessage(),
                teacherException.getCause());

//        var errorStatus = HttpStatus.BAD_REQUEST;
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
