package com.tismart.tsmlibrary.rest;

import android.os.AsyncTask;
import android.os.Environment;

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
 *
 * Clase utilitaria que maneja la descarga inicial de una base de datos.
 */
@SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
public class DatabaseDownloader {

    public static void execute(String url_db, String app_name, String db_name, final DatabaseDownloadListener listener) {
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
                    urlConnection.setConnectTimeout(1000 * 60);
                    urlConnection.setReadTimeout(1000 * 60);
                    urlConnection.connect();
                    long mTotalSize = urlConnection.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream(),
                            8192);
                    File f = new File(Environment.getExternalStorageDirectory(), strings[1]);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    OutputStream output = new FileOutputStream(
                            Environment.getExternalStorageDirectory()
                                    + File.separator + strings[1] + File.separator + strings[2]);
                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress((double) (total / mTotalSize));
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
        }.execute(url_db, app_name, db_name);
    }
}
