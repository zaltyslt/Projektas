package lt.techin.Schedule.tools;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidator implements ConstraintValidator<TextValid, CharSequence> {

    private int textMaximumLength;
    private Pattern validSymbolsPattern;

    @Override
    public void initialize(TextValid constraintAnnotation) {
        textMaximumLength = constraintAnnotation.textMaximumLength();
        rebuildPattern();
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

    //In a case Annotation check isn't enough
    public boolean isTextValid (CharSequence textToCheck, int textMaximumLength) {
        this.textMaximumLength = textMaximumLength;
        rebuildPattern();
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /* The pattern matches strings that start with 1 to (int textMaximumLength) characters,
    consisting of letters (uppercase or lowercase), digits, commas, dashes, dots or spaces.
    */
    private void rebuildPattern () {
        this.validSymbolsPattern = Pattern.compile("^[A-Za-z0-9-,.'\\s]{1," + textMaximumLength + "}$");
    }

    //In a case new object creation should be avoided
    public static boolean isTextValidStatic (CharSequence textToCheck, int textMaximumLength) {
        Pattern validSymbolsPattern = Pattern.compile("^[A-Za-z0-9-,.'\\s]{1," + textMaximumLength + "}$");
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
