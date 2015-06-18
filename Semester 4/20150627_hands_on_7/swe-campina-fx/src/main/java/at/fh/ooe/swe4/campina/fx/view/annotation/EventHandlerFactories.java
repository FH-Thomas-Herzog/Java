package at.fh.ooe.swe4.campina.fx.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.fh.ooe.swe4.campina.fx.view.api.EventHandlerFactory;

/**
 * This annotation allows to define event listener factories for form field
 * annotated methods.
 * 
 * @author Thomas Herzog <thomas.herzog@students.fh-hagenberg.at>
 * @date Jun 5, 2015
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface EventHandlerFactories {

	/**
	 * @return the array of event handler factories.
	 */
	Class<? extends EventHandlerFactory>[] value() default {};
}