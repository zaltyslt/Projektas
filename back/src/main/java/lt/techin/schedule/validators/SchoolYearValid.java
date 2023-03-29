package lt.techin.schedule.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = SchoolYearValidator.class)
@Documented
public @interface SchoolYearValid {
    String message() default "Mokslo metai yra negalimi";

    int textMaximumLength() default 50;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
