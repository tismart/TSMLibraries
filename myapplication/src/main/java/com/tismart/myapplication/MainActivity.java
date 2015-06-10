package com.tismart.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tismart.tsmlibrary.rest.RestClient;
import com.tismart.tsmlibrary.rest.enums.AmbienteEnum;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;
import com.tismart.tsmlibrary.rest.interfaces.RestCallback;
import com.tismart.tsmlytics.TSMLytics;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);

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
    }

    private class WebServiceRestClient extends RestClient {

        android.net.NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        WebServiceRestClient() {
            DES_URL = "http://192.168.1.171:1991/EsikaRestSlim/index.php/pack_product";
            ambienteEnum = AmbienteEnum.DESARROLLO;
        }
    }
}
