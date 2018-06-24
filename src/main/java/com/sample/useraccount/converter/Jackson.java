package com.sample.useraccount.converter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by anoopbalakrishnankunnath on 12/21/17.
 */
@Target(value = { ElementType.TYPE, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented()
public @interface Jackson {
}
