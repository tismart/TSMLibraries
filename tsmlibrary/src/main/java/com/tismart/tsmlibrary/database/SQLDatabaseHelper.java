package com.tismart.tsmlibrary.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Luis Miguel on 23/01/2015.
 * Clase abstracta que permite generar una base de datos y cuenta con la opción de generar una bd desde Assets o desde una descarga vía web.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class SQLDatabaseHelper extends SQLiteOpenHelper {

    final private String DB_NAME;

    final private Context context;
    final private String DB_PATH;

    public SQLDatabaseHelper(Context context, String db_name, int db_version) {
        super(context, db_name, null, db_version);
        this.context = context;

        DB_PATH = context.getDatabasePath(db_name).getPath().replace(db_name, "");

        DB_NAME = db_name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase(boolean fromAssets) throws IOException, DatabaseNotFoundException {
        if (!existsDatabase()) {
            if (fromAssets) {
                copyDatabaseFromAssets();
            } else {
                copyDatabase();
            }
        }
    }

    private void copyDatabaseFromAssets() throws IOException {
        File f;
        InputStream myInput;
        byte[] buffer = new byte[1024];
        int length;
        myInput = context.getAssets().open(DB_NAME);
        f = new File(DB_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void copyDatabase() throws IOException, DatabaseNotFoundException {
        File f;
        InputStream myInput;
        byte[] buffer = new byte[1024];
        int length;
        f = new File(getDownloadDBFile());
        if (!f.exists()) {
            throw new DatabaseNotFoundException();
        }
        myInput = new FileInputStream(getDownloadDBFile());
        f = new File(DB_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        OutputStream myOutput = new FileOutputStream(DB_PATH + DB_NAME);
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        context.deleteFile(DB_NAME);
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public boolean existsDatabase() {
        File f;

        Cursor cursor;
        f = new File(DB_PATH + DB_NAME);

        if (!f.exists()) {
            return false;
        }
        try {
            SQLiteDatabase db = getReadableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) as NumeroTablas FROM sqlite_master where type = 'table' AND name <> 'android_metadata'", null);
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                cursor.close();
                db.close();
                if (count > 0) {

                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String getDownloadDBFile() {
        return context.getCacheDir() + File.separator + DB_NAME;
    }

}
