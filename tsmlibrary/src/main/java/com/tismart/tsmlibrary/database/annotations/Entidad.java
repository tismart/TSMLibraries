package com.tismart.tsmlibrary.database.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Luis Miguel on 23/01/2015.
 *
 * Permite relacionar la entidad con la entidad del sqlite.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Entidad {
    String tableName() default "";
}
