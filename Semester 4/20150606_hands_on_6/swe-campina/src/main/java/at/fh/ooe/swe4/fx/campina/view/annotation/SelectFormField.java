package at.fh.ooe.swe4.fx.campina.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.fh.ooe.swe4.fx.campina.view.form.FormUtils.FormFieldType;
import javafx.util.StringConverter;

/**
 * This annotation marks an valid java bean getter method as an data provider
 * for an select field.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SelectFormField {

	/**
	 * @return property name of the form field with type
	 *         {@link FormFieldType#SELECT}.<br>
	 *         E.g.: target="day" -> getDays
	 */
	String target();

	/**
	 * @return An converter used for the backed form field of type
	 *         {@link FormFieldType#SELECT}.
	 */
	Class<? extends StringConverter> converter() default StringConverter.class;
}
