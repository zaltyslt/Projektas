package lt.techin.schedule.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator extends ValidatorBase implements ConstraintValidator<EmailValid, CharSequence> {
    private int nameMaximumLength;
    private int addressMaximumLength;
    private final static String validSymbols = "A-Za-z0-9-_~.\\+";

    @Override
    public void initialize(EmailValid constraintAnnotation) {
        nameMaximumLength = constraintAnnotation.nameMaximumLength();
        addressMaximumLength = constraintAnnotation.addressMaximumLength();
        buildPattern();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return super.isValid(charSequence, constraintValidatorContext);
    }

    /*
    The pattern matches strings that start with 1 to (int nameMaximumLength) characters, consisting
    of letters (uppercase or lowercase), digits, dashes, dots, pluses, tildes, has @ in center
    and ends with the aforementioned symbols which do not exceed address length (int addressMaximumLength)
    */
    @Override
    protected void buildPattern() {
        {
            super.validSymbolsPattern = Pattern.compile(
                    "^[" + validSymbols + "]{1," + nameMaximumLength + "}" +
                            "@[" + validSymbols + "]{1," + addressMaximumLength + "}$");
        }
    }

    //Static to avoid creating unnecessary objects
    public static boolean isEmailValid(CharSequence textToCheck, int nameMaximumLength, int addressMaximumLength) {
        return ValidatorBase.isCharSequenceValid(textToCheck, Pattern.compile(
                "^[" + validSymbols + "]{1," + nameMaximumLength + "}" +
                        "@[" + validSymbols + "]{1," + addressMaximumLength + "}$"));
    }
}
