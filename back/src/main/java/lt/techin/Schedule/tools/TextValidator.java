package lt.techin.Schedule.tools;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator implements ConstraintValidator<TextValid, CharSequence> {

    /* The pattern matches strings that start with 1 to 20 characters,
    consisting of letters (uppercase or lowercase), digits, commas, dashes, dots or spaces.
    */
    private static final Pattern validSymbolsPattern = Pattern.compile("^[A-Za-z0-9-,.'\\s]{1,50}$");

    @Override
    public boolean isValid(CharSequence textToCheck, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    //In case a faster check without annotation is needed
    public static boolean isTextValid (CharSequence textToCheck) {
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
