package com.tismart.tsmlibrary.database.annotations;

import com.tismart.tsmlibrary.database.enums.TipoElemento;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Luis Miguel on 23/01/2015.
 *
 * Interface que permite definir que tipo de elemento tiene el atributo al momento de obtenerse de base de datos.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Elemento {
    String columnName() default "";

    TipoElemento elementType() default TipoElemento.STRING;

    boolean isPrimary() default false;
}
