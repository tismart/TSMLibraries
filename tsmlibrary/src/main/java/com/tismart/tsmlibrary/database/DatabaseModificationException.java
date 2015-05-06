package com.tismart.tsmlibrary.database;

/**
 * Created by luis.burgos on 06/03/2015.
 * <p/>
 * Clase excepción para controlar cuando no se realiza una inserción o actualización de bd.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class DatabaseModificationException extends Exception {

    public DatabaseModificationException() {
        super();
    }

    public DatabaseModificationException(String detailMessage) {
        super(detailMessage);
    }
}
