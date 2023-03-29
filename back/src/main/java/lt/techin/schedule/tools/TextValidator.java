package lt.techin.schedule.tools;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lt.techin.schedule.validators.TextValid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator implements ConstraintValidator<TextValid, CharSequence> {
    private int textMaximumLength;
    private Pattern validSymbolsPattern;

    @Override
    public void initialize(TextValid constraintAnnotation) {
        textMaximumLength = constraintAnnotation.textMaximumLength();
        buildPattern();
    }

    @Override
    public boolean isValid(CharSequence textToCheck, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    private void buildPattern() {
        this.validSymbolsPattern = Pattern.compile("^[A-Za-z0-9-,.'\\s]{1," + textMaximumLength + "}$");
    }

    public static boolean isTextValid(CharSequence textToCheck, int textMaximumLength) {
        Pattern validSymbolsPattern = Pattern.compile("^[A-Za-z0-9-,.'\\s]{1," + textMaximumLength + "}$");
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
