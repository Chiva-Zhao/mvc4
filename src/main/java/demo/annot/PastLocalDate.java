package demo.annot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PastLocalDate.PastValidator.class)
public @interface PastLocalDate {
	String message() default "{javax.validation.constraints.Past message}";

	Class<?>[]groups() default {};

	Class<?>[]payload() default {};

	class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate> {

		@Override
		public void initialize(PastLocalDate constraintAnnotation) {

		}

		@Override
		public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
			return value == null || value.isBefore(LocalDate.now());
		}

	}

}
