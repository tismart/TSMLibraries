package com.tismart.tsmlibrary.database.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Luis Miguel on 23/01/2015.
 * Interface que permite definir que tipo de elemento tiene el atributo al momento de obtenerse de base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Element {
    String ColumnName();

    com.tismart.tsmlibrary.database.enums.ElementType TypeElement() default com.tismart.tsmlibrary.database.enums.ElementType.STRING;

    boolean IsNull() default false;
}
