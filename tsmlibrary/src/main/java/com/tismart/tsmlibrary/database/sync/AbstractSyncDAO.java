package com.tismart.tsmlibrary.database.sync;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class AbstractSyncDAO<T> {

    final private String tableName;
    final private Class<T> clase;
    final private SQLiteDatabase db = SyncDatabaseInstance.getInstance().getDatabase();
    protected HashMap<String, Integer> lstColumnIndex;
    private HashMap<Field, Elemento> lstFieldElemento;
    private String columnFlag;

    //region Inicialización
    protected AbstractSyncDAO(Class<T> entidad, String columnFlag) {
        Entidad annotation = entidad.getAnnotation(Entidad.class);
        tableName = annotation.tableName().length() > 0 ? annotation.tableName() : entidad.getSimpleName();
        clase = entidad;
        this.columnFlag = columnFlag;

        inicializeFieldElement();
        inicializeColumnIndex();
    }

    private void inicializeFieldElement() {
        if (lstFieldElemento == null) {
            lstFieldElemento = new HashMap<>();

            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Elemento.class)) {
                    lstFieldElemento.put(f, f.getAnnotation(Elemento.class));
                }
            }
        }
    }

    private void inicializeColumnIndex() {
        if (lstColumnIndex == null) {
            lstColumnIndex = new HashMap<>();
            for (Elemento elemento : lstFieldElemento.values()) {
                lstColumnIndex.put(elemento.columnName(), -1);
            }
        }
    }

    //endregion

    /**
     * @param flagValue Para listar los elementos con el valor designado.
     * @return Listado con las entidades.
     */
    public ArrayList<T> listEntities(String flagValue) {
        ArrayList<T> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    tableName,
                    null,
                    columnFlag + " like '" + flagValue + "'",
                    null,
                    null,
                    null,
                    null,
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    lst.add(transformCursorToEntity(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lst;
    }

    T transformCursorToEntity(Cursor cursor) {
        try {
            T entidad = clase.newInstance();
            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (lstFieldElemento.containsKey(f)) {
                    Elemento elemento = lstFieldElemento.get(f);

                    if (lstColumnIndex.get(elemento.columnName()) == -1) {
                        lstColumnIndex.put(elemento.columnName(), cursor.getColumnIndex(elemento.columnName()));
                    }

                    switch (elemento.elementType()) {
                        case LONG:
                            if (elemento.isNull()) {
                                f.set(entidad, transformCursorToLongNull(elemento.columnName(), cursor));
                            } else {
                                f.setLong(entidad, transformCursorToLong(elemento.columnName(), cursor));
                            }
                            break;
                        case BLOB:
                            break;
                        case DOUBLE:
                            if (elemento.isNull()) {
                                f.set(entidad, transformCursorToDoubleNull(elemento.columnName(), cursor));
                            } else {
                                f.setDouble(entidad, transformCursorToDouble(elemento.columnName(), cursor));
                            }
                            break;
                        case BOOLEAN:
                            if (elemento.isNull()) {
                                f.set(entidad, transformCursorToBooleanNull(elemento.columnName(), cursor));
                            } else {
                                f.setBoolean(entidad, transformCursorToBoolean(elemento.columnName(), cursor));
                            }
                            break;
                        case STRING:
                            if (elemento.isNull()) {
                                f.set(entidad, transformCursorToStringNull(elemento.columnName(), cursor));
                            } else {
                                f.set(entidad, transformCursorToString(elemento.columnName(), cursor));
                            }
                            break;
                        case INTEGER:
                            if (elemento.isNull()) {
                                f.set(entidad, transformCursorToIntNull(elemento.columnName(), cursor));
                            } else {
                                f.setInt(entidad, transformCursorToInt(elemento.columnName(), cursor));
                            }
                            break;
                    }
                }
            }
            return entidad;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private int transformCursorToInt(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? 0 : cursor.getInt(lstColumnIndex.get(name));
    }

    private Integer transformCursorToIntNull(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? null : cursor.getInt(lstColumnIndex.get(name));
    }

    private double transformCursorToDouble(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? 0d : cursor.getDouble(lstColumnIndex.get(name));
    }

    private Double transformCursorToDoubleNull(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? null : cursor.getDouble(lstColumnIndex.get(name));
    }

    private long transformCursorToLong(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? 0l : cursor.getLong(lstColumnIndex.get(name));
    }

    private Long transformCursorToLongNull(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? null : cursor.getLong(lstColumnIndex.get(name));
    }

    private String transformCursorToString(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? "" : cursor.getString(lstColumnIndex.get(name));
    }

    private String transformCursorToStringNull(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? null : cursor.getString(lstColumnIndex.get(name));
    }

    private boolean transformCursorToBoolean(String name, Cursor cursor) {
        return transformCursorToInt(name, cursor) == 1;
    }

    private Boolean transformCursorToBooleanNull(String name, Cursor cursor) {
        Integer integer = transformCursorToIntNull(name, cursor);
        return integer == null ? null : integer == 1;
    }
}
