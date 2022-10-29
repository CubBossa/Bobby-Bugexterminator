package de.cubbossa.testing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	int timeout() default 0;
}
