package com.tismart.tsmlibrary.rest.interfaces;

/**
 * Created by luis.burgos on 14/05/2015.
 * <p/>
 * Interface que cuenta con los métodos necesarios una vez iniciada la descarga de la bd.
 */
@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
public interface DatabaseDownloadListener {
    void onStart();

    void publishProgress(double progress);

    void onCompleted();

    void onError();
}
