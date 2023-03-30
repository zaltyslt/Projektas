package lt.techin.schedule.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SchoolYearValidator extends ValidatorBase implements ConstraintValidator<SchoolYearValid, CharSequence> {
    private int textMaximumLength;
    private final static String VALID_SYMBOLS = "0-9-/";

    @Override
    public void initialize(SchoolYearValid constraintAnnotation) {
        textMaximumLength = constraintAnnotation.textMaximumLength();
        buildPattern();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        ValidationDto validationDto = new ValidationDto();
        validationDto.addDatabaseError(validationDto.toString().split("validationErrors=")[1]);
        validationDto.ValidateDatabaseError(VALID_SYMBOLS);
        return super.isValid(charSequence, constraintValidatorContext);
    }

    @Override
    protected void buildPattern() {
        super.validSymbolsPattern =
                Pattern.compile("^[" + VALID_SYMBOLS + "]{1," + textMaximumLength + "}$");
    }

    public static boolean isSchoolYearValid(CharSequence textToCheck, int textMaximumLength) {
        return ValidatorBase.isCharSequenceValid(textToCheck,
                Pattern.compile("^[" + VALID_SYMBOLS + "]{1," + textMaximumLength + "}$"));
    }
}
