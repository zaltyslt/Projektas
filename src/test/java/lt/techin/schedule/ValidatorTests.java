package lt.techin.schedule;

import lt.techin.schedule.validators.EmailValidator;
import lt.techin.schedule.validators.PhoneNumberValidator;
import lt.techin.schedule.validators.SchoolYearValidator;
import lt.techin.schedule.validators.TextValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTests {

    @Test
    public void testTextValidator() {
        Assertions.assertTrue(TextValidator.isTextValid("IShouldBeValid", 50), "Simple text containing letters should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Be Valid", 50), "Simple text containing letters and spaces should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I'm0123456789Should.Still,Be-Valid", 50), "Text containing letters, spaces, digits, commas, dashes, and dots should be valid");

        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols %", 50), "Symbol % should be invalid");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols $", 50), "Symbol $ should be invalid");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols =", 50), "Symbol = should be invalid");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols +", 50), "Symbol + should be invalid");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Symbols *", 50), "Symbol * should be invalid");

        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols ?", 50), "Symbol ? should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols /", 50), "Symbol / should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols \"", 50), "Symbol \" should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols []", 50), "Symbols [] should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols ()", 50), "Symbols () should be valid");
        Assertions.assertTrue(TextValidator.isTextValid("I Should Have Legal Symbols :;", 50), "Symbols :; should be valid");

        Assertions.assertFalse(TextValidator.isTextValid("This Text Is 51 Symbols Long12012345678901234567890", 50), "Text shouldn't exceed 50 symbols");
        Assertions.assertFalse(TextValidator.isTextValid("I Should Have Illegal Length Because This Text WIll Exceed 50 Symbols And Will Be Really Really Long " +
                "So Long That You Should Never Read Me Until The End", 50), "Text shouldn't exceed 50 symbols");

        Assertions.assertFalse(TextValidator.isTextValid("This", 2), "Text shouldn't exceed the length method received");
        Assertions.assertFalse(TextValidator.isTextValid("This Text Is Too Long For Test To Work", 25), "Text shouldn't exceed the length method received");
    }

    @Test
    public void testEmailValidator() {
        Assertions.assertTrue(EmailValidator.isEmailValid("IShouldBeValid@gmail.com", 30, 30), "Simple email should be valid");
        Assertions.assertTrue(EmailValidator.isEmailValid("I_Should-Be01Valid~+@stuff.lt", 30, 30), "Email containing dashes and digits should be valid");
        Assertions.assertTrue(EmailValidator.isEmailValid("I_Should_Be_Valid47@gmail.hotmail.net", 30, 30), "Email containing dashes, digits and " +
                "address with multiple dots should be valid");

        Assertions.assertFalse(EmailValidator.isEmailValid("I Shouldn't Be Valid@gmail.com", 30, 30), "Email containing spaces should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("%IShouldn'tBeValid@gmail.com", 30, 30), "Email containing % should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("$IShouldn'tBeValid@gmail.com", 30, 30), "Email containing $ should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("/IShouldn'tBeValid@gmail.com", 30, 30), "Email containing / should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid(",IShouldn'tBeValid@gmail.com", 30, 30), "Email containing , should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("=IShouldn'tBeValid@gmail.com", 30, 30), "Email containing = should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBe;Valid@gmail.com", 30, 30), "Email containing ; should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBe:Valid@gmail.com", 30, 30), "Email containing : should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn't()BeValid@gmail.com", 30, 30), "Email containing () should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn't[]BeValid@gmail.com", 30, 30), "Email containing [] should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn't{}BeValid@gmail.com", 30, 30), "Email containing {} should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBe`Valid@gmail.com", 30, 30), "Email containing ` should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBe|Valid@gmail.com", 30, 30), "Email containing | should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBeValid@gmail*.com", 30, 30), "Email containing * should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBeValid@gmail&.com", 30, 30), "Email containing & should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("IShouldn'tBeValid@gmail<>.com", 30, 30), "Email containing <> should be invalid");

        Assertions.assertFalse(EmailValidator.isEmailValid("myemail.hotmail.com", 30, 30), "Email format should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("myemailiswrong_gmail", 30, 30), "Email format should be invalid");

        Assertions.assertFalse(EmailValidator.isEmailValid("MyNameIsTooLong@gmail.com", 14, 30), "Email exceeding maximum length of name should be invalid");
        Assertions.assertFalse(EmailValidator.isEmailValid("MyAdressIsTooLong@gmail.com", 30, 8), "Email exceeding maximum length of address should be invalid");
    }

    @Test
    public void testPhoneNumberValidator() {
        Assertions.assertTrue(PhoneNumberValidator.isPhoneNumberValid("86457321", 30), "Simple phone number should be valid");
        Assertions.assertTrue(PhoneNumberValidator.isPhoneNumberValid("+370 645 24 345", 30), "Phone number with + and spaces should be valid");
        Assertions.assertTrue(PhoneNumberValidator.isPhoneNumberValid("(62)64524345", 30), "Phone number with parentheses should be valid");

        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("This Is Text", 30), "Phone number consisting of letters should be invalid");
        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("1d3543s87", 30), "Phone number containing letters should be invalid");
        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("546785378-6", 30), "Phone number containing - symbol should be invalid");
        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("865543*87", 30), "Phone number containing * symbol should be invalid");
        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("865543@#$%^&=_{}[]|/<>87", 30), "Phone number containing any symbols should be invalid");

        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("01234567891", 10), "Phone number exceeding maximum length should be invalid");
        Assertions.assertFalse(PhoneNumberValidator.isPhoneNumberValid("12", 1), "Phone number exceeding maximum length should be invalid");
    }

    @Test
    public void testSchoolYearValidator() {
        Assertions.assertTrue(SchoolYearValidator.isSchoolYearValid("2000", 30), "Simple school year should be valid");
        Assertions.assertTrue(SchoolYearValidator.isSchoolYearValid("2010-2015", 30), "School year with - symbol should be valid");
        Assertions.assertTrue(SchoolYearValidator.isSchoolYearValid("200/130", 30), "School year with / symbol should be valid");

        Assertions.assertFalse(SchoolYearValidator.isSchoolYearValid("This Is Text", 30), "School year with letters should be invalid");
        Assertions.assertFalse(SchoolYearValidator.isSchoolYearValid("2010!?", 30), "School year with illegal symbols should be invalid");
    }
}
