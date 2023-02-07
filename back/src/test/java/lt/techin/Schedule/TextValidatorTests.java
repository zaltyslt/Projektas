package lt.techin.Schedule;

import lt.techin.Schedule.tools.TextValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TextValidatorTests {

    @Test
    public void testValidator() {
        TextValidator textValidator = new TextValidator();

        Assertions.assertTrue(textValidator.isTextValid("IShouldBeValid", 50), "Simple text containing letters should be valid");
        Assertions.assertTrue(textValidator.isTextValid("I Should Be Valid", 50), "Simple text containing letters and spaces should be valid");
        Assertions.assertTrue(textValidator.isTextValid("I'm0123456789Should.Still,Be-Valid", 50), "Text containing letters, spaces, digits, commas, dashes, and dots should be valid");

        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Symbols %", 50), "Symbol % should be illegal");
        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Symbols $", 50), "Symbol $ should be illegal");
        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Symbols =", 50), "Symbol = should be illegal");
        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Symbols +", 50), "Symbol + should be illegal");
        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Symbols *", 50), "Symbol * should be illegal");

        Assertions.assertFalse(textValidator.isTextValid("This Text Is 51 Symbols Long12012345678901234567890", 50), "Text shouldn't exceed 50 symbols");
        Assertions.assertFalse(textValidator.isTextValid("I Should Have Illegal Length Because This Text WIll Exceed 50 Symbols And Will Be Really Really Long " +
                "So Long That You Should Never Read Me Until The End", 50), "Text shouldn't exceed 50 symbols");

        Assertions.assertFalse(textValidator.isTextValid("This", 2), "Text shouldn't exceed the length method received");
        Assertions.assertFalse(textValidator.isTextValid("This Text Is Too Long For Test To Work", 25), "Text shouldn't exceed the length method received");

        Assertions.assertTrue(TextValidator.isTextValidStatic("I'm0123456789Should.Still,Be-Valid", 50), "Text containing letters, spaces, digits, commas, dashes, and dots should be valid in a static method");
        Assertions.assertFalse(TextValidator.isTextValidStatic("I Should Have Illegal Symbols %", 50), "Symbol % should be illegal in a static method");
        Assertions.assertFalse(textValidator.isTextValid("This Text Is 51 Symbols Long12012345678901234567890", 50), "Text shouldn't exceed 50 symbols in a static method");
        Assertions.assertFalse(textValidator.isTextValid("This", 2), "Text shouldn't exceed the length method received in a static method");
    }
}
