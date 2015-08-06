package com.tismart.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tismart.tsmlibrary.rest.FileDownloader;
import com.tismart.tsmlibrary.rest.RestClient;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;
import com.tismart.tsmlibrary.rest.interfaces.DatabaseDownloadListener;
import com.tismart.tsmlibrary.rest.interfaces.RestCallback;
import com.tismart.tsmlytics.TSMLytics;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TSMLytics lytics = new TSMLytics(this);
        lytics.getAllWithEntities();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("color1", "182910");
            jsonObject.put("color2", "625A31");
            jsonObject.put("look", "2");
            new WebServiceRestClient().postAsync(this, "", "", jsonObject, new RestCallback() {
                @Override
                public void OnStart() {

                }

                @Override
                public void OnResponse(ResponseCode responseCode, JSONObject result) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FileDownloader.execute(this, "http://qaelb-webmobile-2113058549.us-east-1.elb.amazonaws.com/FTPSite/FFVVMovil/SQLite/prueba_SSO/BelcorpCore.sqlite", "BelcorpCore.sqlite", new DatabaseDownloadListener() {
            ProgressDialog progressDialog;

            @Override
            public void onStart() {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("Descargando base de datos");
                progressDialog.setTitle("Obteniendo BD");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
                progressDialog.setMax(100);
                progressDialog.show();
            }

            @Override
            public void publishProgress(double progress) {
                progressDialog.setProgress((int) progress);
            }

            @Override
            public void onCompleted() {
                progressDialog.dismiss();
            }

            @Override
            public void onError() {
                progressDialog.cancel();
            }
        });
    }

    private class WebServiceRestClient extends RestClient {

        WebServiceRestClient() {
            super("http://192.168.1.171:1991/EsikaRestSlim/index.php/pack_product");
        }
    }
}
