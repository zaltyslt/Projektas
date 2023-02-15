package lt.techin.schedule.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TextValidator extends ValidatorBase implements ConstraintValidator<TextValid, CharSequence> {

    private int textMaximumLength;
    private final static String validSymbols = "A-Za-z0-9-,.:;)(/?'\"\\s\\]\\[";
    @Override
    public void initialize(TextValid constraintAnnotation) {
        textMaximumLength = constraintAnnotation.textMaximumLength();
        buildPattern();
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
        return super.isValid(charSequence, constraintValidatorContext);
    }

    /*
    The pattern matches strings that start with 1 to (int textMaximumLength) characters,
    consisting of letters (uppercase or lowercase), digits, dashes, commas, dots, colons, semicolons,
    parentheses, slashes, question marks, quotation marks, spaces, square brackets.
    */
    @Override
    protected void buildPattern() {
        {
            super.validSymbolsPattern = Pattern.compile("^[" + validSymbols + "]{1," + textMaximumLength + "}$");
        }
    }

    //Static to avoid creating unnecessary objects
    public static boolean isTextValid (CharSequence textToCheck, int textMaximumLength) {
        return ValidatorBase.isCharSequenceValid(textToCheck, Pattern.compile("^[" + validSymbols + "]{1," + textMaximumLength + "}$"));
    }
}
