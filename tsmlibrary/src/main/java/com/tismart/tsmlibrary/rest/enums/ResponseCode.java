package com.tismart.tsmlibrary.rest.enums;

/**
 * Created by luis.burgos on 08/04/2015.
 * <p/>
 * Enum con los códigos de error comunes retornados por los servicios http.
 */
public enum ResponseCode {

    HTTP_OK(200),
    HTTP_ERROR_NOT_FOUND(404),
    HTTP_ERROR_BAD_REQUEST(400),
    HTTP_ERROR_UNAUTHORIZED(401),
    HTTP_ERROR_FORBIDDEN(403),
    HTTP_ERROR_INTERNAL_SERVER_ERROR(500),
    HTTP_ERROR_BAD_GATEWAY(502),
    HTTP_ERROR_SERVICE_UNAVAILABLE(503),
    HTTP_ERROR_UNRECOGNIZED(0);


    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public static ResponseCode valueOf(int code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.code == code) {
                return responseCode;
            }
        }
        return HTTP_ERROR_UNRECOGNIZED;
    }

    public int getCode() {
        return this.code;
    }
}
