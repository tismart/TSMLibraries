package com.tismart.tsmlibrary.rest.interfaces;

import com.tismart.tsmlibrary.rest.enums.ResponseCode;

import org.json.JSONObject;

/**
 * Created by luis.burgos on 08/04/2015.
 * <p/>
 * Interface usada para las respuestas a llamadas a servicios.
 */

@SuppressWarnings("unused")
public interface RestCallback {
    void OnStart();

    void OnResponse(ResponseCode responseCode, JSONObject result);
}
