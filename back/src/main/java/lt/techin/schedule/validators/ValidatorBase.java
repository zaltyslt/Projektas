package lt.techin.schedule.validators;

import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidatorBase {
    protected Pattern validSymbolsPattern;

    protected boolean isValid(CharSequence textToCheck, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = validSymbolsPattern.matcher(textToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    protected abstract void buildPattern();

    //Static method to check whether CharSequence matches validation
    protected static boolean isCharSequenceValid(CharSequence charSequenceToCheck, Pattern pattern) {
        Matcher matcher = pattern.matcher(charSequenceToCheck);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
