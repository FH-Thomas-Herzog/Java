package at.fh.ooe.swe4.fx.campina.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.fh.ooe.swe4.fx.campina.view.form.FormUtils.FormFieldType;

/**
 * This annotation marks an method as an form field and connects the form field
 * to the model method.<br>
 * This annotation must be placed on valid java bean getter method.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {
					ElementType.METHOD
})
public @interface FormField {

	/**
	 * @return the form field id
	 */
	String id();

	/**
	 * @return the backed form field type
	 */
	FormFieldType type();

	/**
	 * @return the form field label
	 */
	String label();

	/**
	 * @return Message displayed if required but not set. Default ""
	 */
	String requiredMessage() default "";

	/**
	 * @return Flag which marks an form field as required. Default false
	 */
	boolean required() default false;

	/**
	 * @return the ordinal of this form field in the form
	 */
	int ordinal() default 1;

	/**
	 * @return additional value class. Must be used when form field of type
	 *         {@link FormFieldType#SELECT}.
	 */
	Class<?> valueClass() default Object.class;

	/**
	 * An event handler factory which can be used to provide event listener for
	 * the generate form field
	 */
	EventHandlerFactories eventHandlerFactories() default @EventHandlerFactories();
}
