package lt.techin.schedule.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = TextValidator.class)
@Documented
public @interface TextValid {

    String message() default "{Text is invalid}";

    int textMaximumLength() default 50;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
