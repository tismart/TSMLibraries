package com.tismart.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tismart.tsmlytics.TSMLytics;
import com.tismart.tsmlytics.entities.App;
import com.tismart.tsmlytics.enums.TSMLyticsEnum;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        HashMap<TSMLyticsEnum, Object> hashTSMLytics = new TSMLytics(MainActivity.this).getAllWithEntities();
        ArrayList<App> mLstApp = (ArrayList<App>) hashTSMLytics.get(TSMLyticsEnum.AppInfo);

        tv.setText("Numero " + mLstApp.size() + "\n");
        for (int i = 0; i < mLstApp.size(); i++)
            tv.setText(tv.getText() + mLstApp.get(i).getName() + " " + mLstApp.get(i).getPackage() + "\n");
    }
}
