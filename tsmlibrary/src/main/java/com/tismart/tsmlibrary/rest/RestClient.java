package com.tismart.tsmlibrary.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tismart.tsmlibrary.R;
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
 * Clase abstracta que implementa los métodos post y get a usarse en las aplicaciones.
 * Se tienen que setear los valores de las variables DES_URL, QA_URL, PRD_URL y el ambienteEnum para poder obtener las rutas correctamente.
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class RestClient {

    private final static String HTTP_POST_ACCEPT = "Accept";
    private final static String HTTP_POST_CONTENTTYPE = "Content-type";
    private final static String APPLICATION_JSON = "application/json";
    private final static String CHARSET = "UTF-8";

    private final static int SECONDS = 1000;
    private final static int MINUTE = 60 * SECONDS;

    protected final String URL;
    private AsyncTask<String, Void, WebServiceResponse> task;

    protected RestClient(String url) {
        URL = url;
    }

    //region Synchronous Methods
    public WebServiceResponse postSync(Context context, String service, String method, JSONObject request) throws NetworkException, IOException, JSONException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        return processPost(URL + service + method, request, MINUTE);
    }

    public WebServiceResponse postSync(Context context, String service, String method, int timeOut, JSONObject request) throws NetworkException, IOException, JSONException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        return processPost(URL + service + method, request, timeOut);
    }

    public WebServiceResponse getSync(Context context, String service, String method) throws NetworkException, IOException, JSONException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        return processGet(URL + service + method, MINUTE);
    }

    public WebServiceResponse getSync(Context context, String service, String method, int timeOut) throws NetworkException, IOException, JSONException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        return processGet(URL + service + method, timeOut);
    }

    //endregion
    //region Asynchronous Methods
    public void postAsync(Context context, String service, String method, JSONObject request, final RestCallback mCallback) throws NetworkException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        task = new AsyncTask<String, Void, WebServiceResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected WebServiceResponse doInBackground(String... strings) {
                WebServiceResponse wsservice = new WebServiceResponse();
                try {
                    wsservice = processPost(strings[0], new JSONObject(strings[1]),MINUTE);
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }
                return wsservice;
            }

            @Override
            protected void onPostExecute(WebServiceResponse response) {
                mCallback.OnResponse(response.responseCode, response.response);
            }

            @Override
            protected void onCancelled() {
                mCallback.OnResponse(ResponseCode.HTTP_ERROR_UNRECOGNIZED, new JSONObject());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL + service + method, request.toString());
    }

    public void postAsync(Context context, String service, String method, JSONObject request,final int timeOut, final RestCallback mCallback) throws NetworkException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        task = new AsyncTask<String, Void, WebServiceResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected WebServiceResponse doInBackground(String... strings) {
                WebServiceResponse wsservice = new WebServiceResponse();
                try {
                    wsservice = processPost(strings[0], new JSONObject(strings[1]),timeOut);
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }
                return wsservice;
            }

            @Override
            protected void onPostExecute(WebServiceResponse response) {
                mCallback.OnResponse(response.responseCode, response.response);
            }

            @Override
            protected void onCancelled() {
                mCallback.OnResponse(ResponseCode.HTTP_ERROR_UNRECOGNIZED, new JSONObject());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL + service + method, request.toString());
    }

    public void getAsync(Context context, String service, String method, final RestCallback mCallback) throws NetworkException, IOException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        task = new AsyncTask<String, Void, WebServiceResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected WebServiceResponse doInBackground(String... strings) {
                WebServiceResponse wsservice = new WebServiceResponse();
                try {
                    String url = strings[0];
                    wsservice = processGet(url,MINUTE);
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }
                return wsservice;
            }

            @Override
            protected void onPostExecute(WebServiceResponse response) {
                mCallback.OnResponse(response.responseCode, response.response);
            }

            @Override
            protected void onCancelled() {
                mCallback.OnResponse(ResponseCode.HTTP_ERROR_UNRECOGNIZED, new JSONObject());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL + service + method);
    }

    public void getAsync(Context context, String service, String method,final int timeOut, final RestCallback mCallback) throws NetworkException, IOException {
        if (!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(R.string.tsmlibrary_error_conexion));
        }
        task = new AsyncTask<String, Void, WebServiceResponse>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mCallback.OnStart();
            }

            @Override
            protected WebServiceResponse doInBackground(String... strings) {
                WebServiceResponse wsservice = new WebServiceResponse();
                try {
                    String url = strings[0];
                    wsservice = processGet(url,timeOut);
                } catch (IOException | JSONException ex) {
                    ex.printStackTrace();
                }
                return wsservice;
            }

            @Override
            protected void onPostExecute(WebServiceResponse response) {
                mCallback.OnResponse(response.responseCode, response.response);
            }

            @Override
            protected void onCancelled() {
                mCallback.OnResponse(ResponseCode.HTTP_ERROR_UNRECOGNIZED, new JSONObject());
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, URL + service + method);
    }

    //endregion
    //region Process methods
    private WebServiceResponse processPost(String str_url, JSONObject request, int timeOut) throws IOException, JSONException {
        WebServiceResponse wsresponse = new WebServiceResponse();
        Log.i("TSMLibrary - RestClient", "URL: " + str_url);
        Log.i("TSMLibrary - RestClient", "Request: " + request);
        URL url = new URL(str_url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setReadTimeout(timeOut);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty(HTTP_POST_CONTENTTYPE, APPLICATION_JSON);
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(request.toString());
            out.close();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            wsresponse.responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            if (wsresponse.responseCode.equals(ResponseCode.HTTP_OK)) {
                wsresponse.response = new JSONObject(convertStreamToString(in));
            }
        } finally {
            urlConnection.disconnect();
        }
        return wsresponse;
    }

    private WebServiceResponse processGet(String str_url, int timeOut) throws IOException, JSONException {
        WebServiceResponse wsresponse = new WebServiceResponse();
        Log.i("TSMLibrary - RestClient", "URL: " + str_url);
        URL url = new URL(str_url);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setConnectTimeout(timeOut);
            urlConnection.setReadTimeout(timeOut);
            urlConnection.setRequestProperty(HTTP_POST_ACCEPT, APPLICATION_JSON);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            wsresponse.responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            if (wsresponse.responseCode.equals(ResponseCode.HTTP_OK)) {
                wsresponse.response = new JSONObject(convertStreamToString(in));
            }
        } finally {
            urlConnection.disconnect();
        }
        return wsresponse;
    }

    public void cancel() {
        if (task != null && task.getStatus().equals(AsyncTask.Status.RUNNING)) {
            task.cancel(true);
        }
    }

    //endregion

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

    public class WebServiceResponse {
        public ResponseCode responseCode;
        public JSONObject response;

        public WebServiceResponse() {
            responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
            response = new JSONObject();
        }
    }
}
