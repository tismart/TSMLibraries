package com.tismart.tsmlibrary.database;

/**
 * Created by luis.burgos on 06/03/2015.
 *
 * Clase excepción para controlar cuando no se realiza una inserción o actualización de bd.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class DatabaseNotFoundException extends Exception {

    public DatabaseNotFoundException() {
        super();
    }

    public DatabaseNotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
