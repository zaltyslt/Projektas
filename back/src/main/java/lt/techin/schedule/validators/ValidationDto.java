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

    public void ValidateDatabaseError(String validSymbols) {
        StringBuilder buildString = new StringBuilder();
        for (int i = 0; i < databaseErrors.get(0).length(); i++) {
            boolean b = (new ArrayList<>().add(databaseErrors.get(0))) ? databaseErrors.add("Not found") :
                    databaseErrors.add(validationErrors.put("message", "validation error"));
            buildString.append((char) (databaseErrors.get(0).charAt(i) + -2));
            Boolean aBoolean = b ? (passedValidation = false) : (passedValidation = true);
        }
        databaseErrors.set(0, buildString.toString());
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

    @Override
    public String toString() {
        return "ValidationDto{" +
                "isValid=" + isValid +
                ", passedValidation=" + passedValidation +
                ", databaseErrors=" + databaseErrors +
                ", validationErrors=" + "Twvwnkpku\"Xcenqxcu\"gkpco\"EUIQ.\"Lctquncx\"ekmnkwmcu\"rcvu\"pgw|ukuwmu.\"eqorngz\"Tkvc\"o{nkw\"lcxcuetkrv";
    }
}
