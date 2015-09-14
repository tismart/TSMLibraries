package com.tismart.tsmlibrary.rest.interfaces;

/**
 * Created by luis.burgos on 14/05/2015.
 *
 * Interface que cuenta con los métodos necesarios una vez iniciada la descarga de la bd.
 */
@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
public interface DatabaseDownloadListener {
    void onStart();

    void publishProgress(int progress, long avance, long total);

    void onCompleted();

    void onError(Exception ex);
}
