package com.tismart.tsmlibrary.rest;

import android.content.Context;
import android.os.AsyncTask;

import com.tismart.tsmlibrary.rest.interfaces.DatabaseDownloadListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luis.burgos on 14/05/2015.
 * Clase utilitaria que maneja la descarga inicial de una base de datos. La descarga se realizará en la carpeta Cache.
 * Cabe destacar que este método es de solo descarga de la base de datos y se debe eliminar manualmente el archivo.
 */
@SuppressWarnings({"unused", "ResultOfMethodCallIgnored", "WeakerAccess"})
public class FileDownloader {

    private static final int MILISECONDS = 1000;
    private static final int SECONDS = 60;

    /**
     * Clase que ejecuta la descarga de un archivo de base de datos, y la almacena en la carpeta de cache interna con el nombre de la base de datos. Se tiene como timeout 1 minuto.
     * @param context Contexto de la aplicación.
     * @param url url de descarga del archivo.
     * @param filename nombre del archivo.
     * @param listener contiene los métodos para poder seguir la descarga del archivo.
     */
    public static void execute(Context context, String url, String filename, DatabaseDownloadListener listener) {
        executeDownloader(context, url, filename, MILISECONDS * SECONDS, listener);
    }

    /**
     * Clase que ejecuta la descarga de un archivo de base de datos, y la almacena en la carpeta de cache interna con el nombre de la base de datos. Se tiene como timeout 1 minuto.
     * @param context Contexto de la aplicación.
     * @param url url de descarga del archivo.
     * @param filename nombre del archivo.
     * @param connectionTimeout tiempo en milisegundos de espera hasta que de timeout
     * @param listener contiene los métodos para poder seguir la descarga del archivo.
     */
    public static void execute(Context context, String url, String filename, int connectionTimeout, DatabaseDownloadListener listener) {
        executeDownloader(context, url, filename, connectionTimeout, listener);
    }

    private static void executeDownloader(final Context context, String url_db, String db_name, final int connectionTimeout, final DatabaseDownloadListener listener) {
        new AsyncTask<String, Double, Boolean>() {

            @Override
            protected void onPreExecute() {
                listener.onStart();
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(connectionTimeout);
                    urlConnection.setReadTimeout(connectionTimeout);
                    urlConnection.connect();
                    long mTotalSize = urlConnection.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);
                    File f = new File(context.getCacheDir(), strings[1]);
                    OutputStream output = new FileOutputStream(f);
                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress(((double)total / (double)mTotalSize)*100);
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception ex) {
                    return false;
                }

                return true;
            }

            @Override
            protected void onProgressUpdate(Double... values) {
                listener.publishProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean) {
                    listener.onCompleted();
                } else {
                    listener.onError();
                }
            }
        }.execute(url_db, db_name);
    }
}
