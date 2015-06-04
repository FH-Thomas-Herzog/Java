package at.fh.ooe.swe4.fx.campina.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.fh.ooe.swe4.fx.campina.view.util.FormUtils.FormFieldType;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {
					ElementType.METHOD
})
public @interface FormField {

	String id();

	FormFieldType type();

	String label();

	String requiredMessage() default "";

	boolean required() default false;

	int ordinal() default 1;

	EventHandlerFactories eventHandlerFactories() default @EventHandlerFactories();
}
