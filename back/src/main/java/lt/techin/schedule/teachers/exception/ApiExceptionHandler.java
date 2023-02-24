package lt.techin.schedule.teachers.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@ControllerAdvice
public class ApiExceptionHandler {

//      pavyzdys response, Spring sugeneruoto validacijos pranesimo
//        {
//    "timestamp": "\"2023-02-22 12:47\"",
//    "status": 500,
//    "error": "Internal Server Error",
//    "path": "/api/v1/teachers/create"
//}

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpServletRequest request, Exception ex) {
        logger.info("SQLException Occured:: URL=" + request.getRequestURL());
        return "database_error";
    }

//    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
//    @ExceptionHandler(IOException.class)
//    public void handleIOException() {
//        logger.error("IOException handler executed");
//        //returning 404 error code
//    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDto> handleDataAccessException(HttpServletRequest request, DataAccessException dataAccessException) {
        logger.error("DataAccessException: {}. Cause?: {}",
                dataAccessException.getMessage(), dataAccessException.getMostSpecificCause().getMessage());

        HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        var errorDto = new ErrorDto(request.getRequestURL().toString(),
                dataAccessException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),

                LocalDateTime.now());

        return ResponseEntity.internalServerError().body(errorDto);
    }

    @ExceptionHandler(TeacherException.class)
    public ResponseEntity<ErrorDto> handleTeacherException(HttpServletRequest request, TeacherException teacherException) {
        logger.error("TeacherException: {}, for field: {}",
                teacherException.getMessage(),
                teacherException.getCause());

//        var errorStatus = HttpStatus.BAD_REQUEST;
        var errorStatus = teacherException.getStatus();
        var message = teacherException.getMessage();
//public ErrorDto(
// String url,
// String message,
// Integer status,
// String error,
// String path,
// LocalDateTime timestamp) {
        var errorDto = new ErrorDto(
                request.getRequestURL().toString(),
                teacherException.getMessage(),
                errorStatus.value(),
                errorStatus.getReasonPhrase(),
//                errorStatus.name(),
                LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDto);
    }


//    @ExceptionHandler(ZooServiceDisabledException.class)
//    public ResponseEntity<Void> handleZooServiceDisabledException(HttpServletRequest request, ZooServiceDisabledException serviceDisabledException) {
//        logger.error("ZooServiceDisabledException: {}", serviceDisabledException.getMessage());
//
//        var errorStatus = HttpStatus.SERVICE_UNAVAILABLE;
//
//        return new ResponseEntity<>(errorStatus);
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException notValidException) {
//        logger.error("MethodArgumentNotValidException: {}", notValidException.getMessage());
//
//        var errorStatus = HttpStatus.BAD_REQUEST;
//
//        var errorFields = notValidException.getBindingResult()
//                .getAllErrors().stream()
//                .map(ErrorFieldMapper::toErrorFieldDto)
//                .collect(toList());
//
//        var errorDto = new ErrorDto(request.getRequestURL().toString(),
//                errorFields,
//                notValidException.getMessage(),
//                errorStatus.value(),
//                errorStatus.getReasonPhrase(),
//                request.getRequestURL().toString(),
//                LocalDateTime.now());
//        return ResponseEntity.badRequest().body(errorDto);
//    }
//
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "We do not support this")
//    @ExceptionHandler(HttpMediaTypeException.class)
//    public void handleHttpMediaTypeException(HttpMediaTypeException mediaTypeException) {
//        logger.error("Not supported request resulted in HttpMediaTypeException: {}", mediaTypeException.getMessage());
//    }
//
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Something really bad happened")
//    @ExceptionHandler(Exception.class)
//    public void handleAllExceptions(Exception exception) {
//        logger.error("All Exceptions handler: {}", exception.getMessage());
//    }

}