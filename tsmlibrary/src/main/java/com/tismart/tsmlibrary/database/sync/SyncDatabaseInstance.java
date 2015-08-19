package com.tismart.tsmlibrary.database.sync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tismart.tsmlibrary.database.DatabaseNotFoundException;
import com.tismart.tsmlibrary.database.SQLDatabaseHelper;

import java.io.IOException;

/**
 * Created by Luis Miguel on 23/01/2015.
 * Clase que usa el patrón singleton para instanciar el helper que comunica la aplicación con la bd.
 */

@SuppressWarnings({"unused", "SameParameterValue"})
public class SyncDatabaseInstance {
    private static SyncDatabaseInstance ourInstance = null;
    final private Context context;
    private final String db_name;
    private final int db_version;
    public boolean existDatabase = false;
    private SQLiteDatabase db;
    private SQLDatabaseHelper dbHelper;

    private SyncDatabaseInstance(Context context, String db_name, int db_version, boolean fromAssets) {
        dbHelper = new SQLDatabaseHelper(context, db_name, db_version);
        this.context = context;
        this.db_name = db_name;
        this.db_version = db_version;
        try {
            dbHelper.createDatabase(fromAssets);
            existDatabase = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatabaseNotFoundException dnfe) {
            existDatabase = false;
        }
    }

    public static void initialize(Context context, String db_name, int db_version, boolean fromAssets) {
        ourInstance = new SyncDatabaseInstance(context, db_name, db_version, fromAssets);
    }

    public static SyncDatabaseInstance getInstance() {
        return ourInstance;
    }

    public SQLDatabaseHelper getDBHelper() {
        if (dbHelper == null) {
            dbHelper = new SQLDatabaseHelper(context, db_name, db_version);
        }
        return dbHelper;
    }

    public SQLiteDatabase getDatabase() {
        if (db == null) {
            db = getDBHelper().getWritableDatabase();
        }
        return db;
    }

    public boolean existsDatabase() {
        existDatabase = getDBHelper().existsDatabase();
        return existDatabase;
    }

    public void deleteDatabase() {
        context.deleteDatabase(db_name);
    }
}