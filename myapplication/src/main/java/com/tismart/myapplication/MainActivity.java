package com.tismart.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tismart.tsmlytics.entities.Network;


public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

//        HashMap<TSMLyticsEnum, Object> hashTSMLytics = new TSMLytics(MainActivity.this).getAllWithEntities();
//        Network network = (Network) hashTSMLytics.get(TSMLyticsEnum.NetworkInfo);
        Network network = null;

        android.net.NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null)
            network = null;
        else {

            tv.setText("Numero " + networkInfo.getType() + "\n" + networkInfo.getSubtype());
        }
    }
}
