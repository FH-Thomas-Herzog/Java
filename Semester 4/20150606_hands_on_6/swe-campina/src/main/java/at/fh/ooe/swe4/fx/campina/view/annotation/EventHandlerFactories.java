package at.fh.ooe.swe4.fx.campina.view.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import at.fh.ooe.swe4.fx.campina.view.event.api.EventHandlerFactory;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.ANNOTATION_TYPE)
public @interface EventHandlerFactories {

	Class<? extends EventHandlerFactory>[] value() default {};
}