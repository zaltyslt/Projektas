package lt.techin.schedule.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneNumberValidator extends ValidatorBase implements ConstraintValidator<PhoneNumberValid, CharSequence> {
    private int maximumLength;
    private final static String validSymbols = "0-9+)(\\s";

    @Override
    public void initialize(PhoneNumberValid constraintAnnotation) {
        maximumLength = constraintAnnotation.maximumLength();
        buildPattern();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return super.isValid(charSequence, constraintValidatorContext);
    }

    /*
    The pattern matches strings that contain only numbers, pluses, parentheses, spaces
    and doesn't exceed length (int maximumLength)
    */
    @Override
    protected void buildPattern() {
        super.validSymbolsPattern = Pattern.compile("^[" + validSymbols + "]{1," + maximumLength + "}$");
    }

    //Static to avoid creating unnecessary objects
    public static boolean isPhoneNumberValid(CharSequence textToCheck, int maximumLength) {
        return ValidatorBase.isCharSequenceValid(textToCheck,
                Pattern.compile("^[" + validSymbols + "]{1," + maximumLength + "}$"));
    }
}
