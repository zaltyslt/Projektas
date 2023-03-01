package lt.techin.schedule.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidationDto {

    private Boolean isValid;
    private Boolean passedValidation;

    private final List<String> databaseErrors;
    private final HashMap<String, String> validationErrors;

    public ValidationDto() {
        databaseErrors = new ArrayList<>();
        validationErrors = new HashMap<>();
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Boolean getPassedValidation() {
        return passedValidation;
    }

    public void setPassedValidation(Boolean passedValidation) {
        this.passedValidation = passedValidation;
    }

    public List<String> getDatabaseErrors() {
        return databaseErrors;
    }

    public void addDatabaseError(String databaseError) {
        databaseErrors.add(databaseError);
    }

    public HashMap<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void addValidationError(String validationErrorFieldName, String validationErrorText) {
        validationErrors.put(validationErrorFieldName, validationErrorText);
    }
}
