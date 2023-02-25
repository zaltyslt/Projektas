package lt.techin.schedule.exceptions;

public class SubjectValidationException extends RuntimeException {

    private String field;
    private String error;

    private String rejectedValue;

    public SubjectValidationException() {
    }

    public SubjectValidationException(String message, String field, String error, String rejectedValue) {
        super(message);
        this.field = field;
        this.error = error;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }

    public String getRejectedValue() {
        return rejectedValue;
    }
}
