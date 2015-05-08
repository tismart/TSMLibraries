package com.tismart.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tismart.myapplication.daos.EntidadDAO;
import com.tismart.myapplication.entities.Entidad;
import com.tismart.tsmlytics.TSMLytics;
import com.tismart.tsmlytics.enums.TSMLyticsEnum;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tv.setText("");
                Entidad entidad = new Entidad();

                TSMLytics tsmLytics = new TSMLytics(MainActivity.this);
                HashMap<TSMLyticsEnum, String> hashTSMLytics = tsmLytics.getAll();

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.AppsOpen.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.AppsOpen) + "\n");
                    entidad.setAppsOpen(Integer.parseInt(hashTSMLytics.get(TSMLyticsEnum.AppsOpen), 0));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.AppsName.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.AppsName) + "\n");
                    entidad.setAppsName(hashTSMLytics.get(TSMLyticsEnum.AppsName));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DeviceBattery.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DeviceBattery) + "\n");
                    entidad.setDeviceBattery(Double.parseDouble(hashTSMLytics.get(TSMLyticsEnum.DeviceBattery)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DeviceType.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DeviceType) + "\n");
                    entidad.setDeviceType(Boolean.parseBoolean(hashTSMLytics.get(TSMLyticsEnum.DeviceType)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DeviceModel.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DeviceModel) + "\n");
                    entidad.setDeviceModel(hashTSMLytics.get(TSMLyticsEnum.DeviceModel));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DeviceID.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DeviceID) + "\n");
                    entidad.setDeviceID(hashTSMLytics.get(TSMLyticsEnum.DeviceID));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DeviceRooted.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DeviceRooted) + "\n");
                    entidad.setDeviceRooted(Boolean.parseBoolean(hashTSMLytics.get(TSMLyticsEnum.DeviceRooted)));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskHDFree.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskHDFree) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskHDUsed.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskHDUsed) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskHDTotal.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskHDTotal) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskSDFree.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskSDFree) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskSDUsed.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskSDUsed) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.DiskSDTotal.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.DiskSDTotal) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.ScreenSize.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.ScreenSize) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.ScreenDensity.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.ScreenDensity) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.ScreenOrientation.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.ScreenOrientation) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.MemoryRAMFree.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.MemoryRAMFree) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.MemoryRAMUsed.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.MemoryRAMUsed) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.MemoryRAMTotal.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.MemoryRAMTotal) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.NetworkConnection.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.NetworkConnection) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.NetworkType.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.NetworkType) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.NetworkStrength.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.NetworkStrength) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.OSName.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.OSName) + "\n");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    tv.setText(tv.getText() + TSMLyticsEnum.OSVersion.toString() + "\t:" + hashTSMLytics.get(TSMLyticsEnum.OSVersion));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                EntidadDAO daoEntidad = new EntidadDAO();
                try {
                    entidad.setId(daoEntidad.getLastId() + 1);
                    daoEntidad.insertEntity(entidad);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                daoEntidad.listEntities(null);
            }
        }, 2000);


    }
}
