package lt.techin.Schedule;

import lt.techin.Schedule.tools.TextValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TextValidatorTest {

    @Test
    public void testValidator() {
        Assertions.assertTrue(TextValidator.isTextValid("IShouldBeValid"), "Simple text containing letters should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Be Valid"), "Simple text containing letters and spaces should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I'm0123456789Should.Still,Be-Valid"), "Text containing letters, spaces, digits, commas, dashes, and dots should be valid");

        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols %"), "Symbol % should be illegal");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols $"), "Symbol $ should be illegal");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols ="), "Symbol = should be illegal");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols +"), "Symbol + should be illegal");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols *"), "Symbol * should be illegal");

        Assertions.assertFalse(TextValidator.isTextValid("This Text Is 51 Symbols Long12012345678901234567890"), "Text shouldn't exceed 50 symbols");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Length Because This Text WIll Exceed 50 Symbols And Will Be Really Really Long " +
                "So Long That You Should Never Read Me Until The End"), "Text shouldn't exceed 50 symbols");
    }
}
