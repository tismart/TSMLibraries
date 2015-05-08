package com.tismart.tsmlibrary.rest;

import android.content.Context;
import android.os.AsyncTask;

import com.tismart.tsmlibrary.R;
import com.tismart.tsmlibrary.rest.enums.AmbienteEnum;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;
import com.tismart.tsmlibrary.rest.exceptions.NetworkException;
import com.tismart.tsmlibrary.rest.interfaces.RestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luis.burgos on 08/04/2015.
 * <p/>
 * Clase abstracta que implementa los métodos post y get a usarse en las aplicaciones.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RestClient {

    private final static String HTTP_POST_ACCEPT = "Accept";
    private final static String HTTP_POST_CONTENTTYPE = "Content-type";
    private final static String APPLICATION_JSON = "application/json";
    private final static String CHARSET = "UTF-8";
    protected AmbienteEnum ambienteEnum;
    protected String DES_URL;
    protected String QA_URL;
    protected String PRD_URL;

    public void post(Context context, String service, String method, JSONObject request, RestCallback mCallback) throws NetworkException, IOException, JSONException {
        ResponseCode responseCode;
        JSONObject jsonResult;
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        URL url = new URL(getUrl() + service + method);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            mCallback.OnStart();
            urlConnection.setDoOutput(true);
            urlConnection.setFixedLengthStreamingMode(request.length());
            urlConnection.setRequestProperty(HTTP_POST_CONTENTTYPE, APPLICATION_JSON);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(request.toString());
            out.close();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            if (responseCode.equals(ResponseCode.HTTP_OK)) {
                jsonResult = new JSONObject(convertStreamToString(in));
            } else {
                jsonResult = new JSONObject();
            }
            mCallback.OnResponse(responseCode, jsonResult);
        } finally {
            urlConnection.disconnect();
        }
    }

    public void postAsync(final Context context, final String service, final String method, final JSONObject request, final RestCallback mCallback) throws NetworkException, IOException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        URL url = new URL(getUrl() + service + method);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        new AsyncTask<HttpURLConnection, Void, String[]>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected String[] doInBackground(HttpURLConnection... urlConnections) {
                ResponseCode responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
                JSONObject jsonResult = new JSONObject();
                try {
                    urlConnections[0].setDoOutput(true);
                    urlConnections[0].setFixedLengthStreamingMode(request.length());
                    urlConnections[0].setRequestProperty(HTTP_POST_CONTENTTYPE, APPLICATION_JSON);
                    OutputStreamWriter out = new OutputStreamWriter(urlConnections[0].getOutputStream());
                    out.write(request.toString());
                    out.close();
                    InputStream in = new BufferedInputStream(urlConnections[0].getInputStream());
                    responseCode = ResponseCode.valueOf(urlConnections[0].getResponseCode());
                    if (responseCode.equals(ResponseCode.HTTP_OK)) {
                        jsonResult = new JSONObject(convertStreamToString(in));
                    }
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                } finally {
                    urlConnections[0].disconnect();
                }
                return new String[]{jsonResult.toString(), responseCode.getCode() + ""};
            }

            @Override
            protected void onPostExecute(String[] strings) {
                JSONObject jsonResponse;
                ResponseCode responseCode;
                try {
                    jsonResponse = new JSONObject(strings[0]);
                    responseCode = ResponseCode.valueOf(Integer.parseInt(strings[1]));
                } catch (JSONException jsonException) {
                    jsonResponse = new JSONObject();
                    responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
                }
                mCallback.OnResponse(responseCode, jsonResponse);
            }
        }.execute(urlConnection);
    }

    public void get(Context context, String service, String method, RestCallback mCallback) throws NetworkException, IOException, JSONException {
        ResponseCode responseCode;
        JSONObject jsonResult;
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        URL url = new URL(getUrl() + service + method);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            mCallback.OnStart();
            urlConnection.setRequestProperty(HTTP_POST_ACCEPT, APPLICATION_JSON);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            if (responseCode.equals(ResponseCode.HTTP_OK)) {
                jsonResult = new JSONObject(convertStreamToString(in));
            } else {
                jsonResult = new JSONObject();
            }
            mCallback.OnResponse(responseCode, jsonResult);
        } finally {
            urlConnection.disconnect();
        }
    }

    public void getAsync(final Context context, final String service, final String method, final RestCallback mCallback) throws NetworkException, IOException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        URL url = new URL(getUrl() + service + method);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        new AsyncTask<HttpURLConnection, Void, String[]>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected String[] doInBackground(HttpURLConnection... urlConnections) {
                ResponseCode responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
                JSONObject jsonResult = new JSONObject();
                try {
                    urlConnections[0].setRequestProperty(HTTP_POST_CONTENTTYPE, APPLICATION_JSON);
                    InputStream in = new BufferedInputStream(urlConnections[0].getInputStream());
                    responseCode = ResponseCode.valueOf(urlConnections[0].getResponseCode());
                    if (responseCode.equals(ResponseCode.HTTP_OK)) {
                        jsonResult = new JSONObject(convertStreamToString(in));
                    }
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                } finally {
                    urlConnections[0].disconnect();
                }
                return new String[]{jsonResult.toString(), responseCode.getCode() + ""};
            }

            @Override
            protected void onPostExecute(String[] strings) {
                JSONObject jsonResponse;
                ResponseCode responseCode;
                try {
                    jsonResponse = new JSONObject(strings[0]);
                    responseCode = ResponseCode.valueOf(Integer.parseInt(strings[1]));
                } catch (JSONException jsonException) {
                    jsonResponse = new JSONObject();
                    responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
                }
                mCallback.OnResponse(responseCode, jsonResponse);
            }
        }.execute(urlConnection);
    }

    private String getUrl() {
        switch (ambienteEnum) {
            case DESARROLLO:
                return DES_URL;
            case QA:
                return QA_URL;
            case PRODUCCION:
                return PRD_URL;
            default:
                return DES_URL;
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = line + "\n";
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
