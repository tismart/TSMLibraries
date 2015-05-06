package com.tismart.tsmlibrary.rest.exceptions;

/**
 * Created by luis.burgos on 08/04/2015.
 * <p/>
 * Excepción personalizada relacionada con problemas de conexión a internet.
 */
@SuppressWarnings("unused")
public class NetworkException extends Exception {

    public NetworkException() {
        super();
    }

    public NetworkException(String detailMessage) {
        super(detailMessage);
    }
}
