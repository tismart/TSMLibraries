package com.tismart.tsmlibrary.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by luis.burgos on 08/04/2015.
 *
 * Clase con metodos auxiliares relacionados con la conexión a internet.
 */
@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "WeakerAccess"})
public class ConnectionUtil {
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
