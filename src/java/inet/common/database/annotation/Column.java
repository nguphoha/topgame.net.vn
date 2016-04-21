package inet.common.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	
	String name();
	boolean PK() default false;
	boolean FK() default false;
	boolean UK() default false;
	boolean filter() default false;
	
}
