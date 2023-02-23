package lt.techin.schedule.teachers.exception;

import org.springframework.http.HttpStatus;

public class TeacherException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public TeacherException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public TeacherException(HttpStatus status, String message, Throwable exception) {
        super(exception);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
