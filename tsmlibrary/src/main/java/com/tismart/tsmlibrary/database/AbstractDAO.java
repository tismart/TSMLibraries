package com.tismart.tsmlibrary.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.tismart.tsmlibrary.database.annotations.Elemento;
import com.tismart.tsmlibrary.database.annotations.Entidad;
import com.tismart.tsmlibrary.database.enums.DatabaseEnum;
import com.tismart.tsmlibrary.database.enums.TipoElemento;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class AbstractDAO<T> {

    protected static HashMap<String, Integer> lstColumnIndex;
    static private ArrayList<Field> lstPrimaryFields;
    private static HashMap<Field, Elemento> lstFieldElemento;
    final private String tableName;
    final private Class<T> clase;
    final private SQLiteDatabase db = DatabaseInstance.getInstance().getDatabase();

    //region Inicialización
    protected AbstractDAO(Class<T> entidad) {
        Entidad annotation = entidad.getAnnotation(Entidad.class);
        tableName = annotation.tableName().length() > 0 ? annotation.tableName() : entidad.getSimpleName();
        clase = entidad;

        inicializeFieldElement();
        inicializeColumnIndex();
        inicializePrimarykey();
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

    private void inicializePrimarykey() {
        if (lstPrimaryFields == null) {
            lstPrimaryFields = new ArrayList<>();
            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (lstFieldElemento.containsKey(f) && lstFieldElemento.get(f).isPrimary()) {
                    lstPrimaryFields.add(f);
                }
            }
        }
    }

    //endregion

    public long insertEntity(T entidad) throws DatabaseModificationException {
        return insertarEntidad(transformEntityToContentValues(entidad));
    }

    public int updateEntity(T entidad) throws IllegalAccessException, DatabaseModificationException {
        ContentValues cv = transformEntityToContentValues(entidad);
        for (Field f : lstPrimaryFields) {
            cv.remove(lstFieldElemento.get(f).columnName());
        }
        return actualizarEntidad(cv, getSelectionIdentifier(entidad));
    }

    //  region Métodos privados
    private long insertarEntidad(ContentValues cv) throws DatabaseModificationException {
        long row_id = db.insert(tableName, null, cv);
        if (row_id == -1) {
            throw new DatabaseModificationException();
        }
        return row_id;
    }

    private int actualizarEntidad(ContentValues cv, String selection) throws DatabaseModificationException {
        int rows_affected = db.update(tableName, cv, selection, null);
        if (rows_affected == 0) {
            throw new DatabaseModificationException();
        }
        return rows_affected;
    }

    public int deleteEntidad(T entidad) throws IllegalAccessException, DatabaseModificationException {
        return removerEntidad(getSelectionIdentifier(entidad));
    }


    private int removerEntidad(String selection) throws DatabaseModificationException {
        int rows_affected = db.delete(tableName, selection, null);
        if (rows_affected == 0) {
            throw new DatabaseModificationException();
        }
        return rows_affected;
    }

    //  region Selection identifier
    private String getSelectionIdentifier(T entidad) throws IllegalAccessException {
        Elemento elemento;
        String mensaje = "";
        int cont = 0;
        for (Field field : lstPrimaryFields) {
            elemento = lstFieldElemento.get(field);
            mensaje = elemento.columnName();
            switch (elemento.elementType()) {
                case STRING:
                case LONG:
                    mensaje += " LIKE '" + field.get(entidad).toString() + "'";
                    break;
                case INTEGER:
                case BOOLEAN:
                    mensaje += " = " + +field.getInt(entidad);
                    break;
                case DOUBLE:
                    mensaje += " = " + +field.getDouble(entidad);
                    break;
                default:
                    mensaje += " LIKE '" + field.get(entidad).toString() + "'";
            }
            if (lstPrimaryFields.size() > cont) {
                mensaje += " AND ";
                cont++;
            }
        }
        return mensaje;
    }

    private String getSelectionIdentifier(Object[] ids) throws IllegalAccessException {
        Elemento elemento;
        String mensaje = "";
        int cont = 0;
        for (Field field : lstPrimaryFields) {
            elemento = lstFieldElemento.get(field);
            mensaje = elemento.columnName();
            switch (elemento.elementType()) {
                case STRING:
                case LONG:
                    mensaje += " LIKE '" + field.get(ids[cont]).toString() + "'";
                    break;
                case INTEGER:
                case BOOLEAN:
                    mensaje += " = " + +field.getInt(ids[cont]);
                    break;
                case DOUBLE:
                    mensaje += " = " + +field.getDouble(ids[cont]);
                    break;
                default:
                    mensaje += " LIKE '" + field.get(ids[cont]).toString() + "'";
            }
            if (lstPrimaryFields.size() > cont) {
                mensaje += " AND ";
                cont++;
            }
        }
        return mensaje;
    }
//  endregion
//  endregion

    /**
     * Solo aplica para entidades que tiene un solo primary key y es del tipo elemento INTEGER o LONG
     *
     * @return id
     */
    public int getLastId() {
        Cursor cursor = null;
        try {
            if (lstPrimaryFields.size() == 1) {
                Elemento elemento = lstPrimaryFields.get(0).getAnnotation(Elemento.class);
                if (elemento.elementType().equals(TipoElemento.INTEGER) || elemento.elementType().equals(TipoElemento.LONG)) {
                    cursor = db.query(tableName, new String[]{elemento.columnName()}, null, null, null, null, elemento.columnName() + " DESC");
                    if (cursor != null && cursor.moveToFirst()) {
                        return cursor.getInt(0);
                    }
                    return 1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return -1;
    }


    /**
     * @param args Para listar los elementos de las entidades se puede enviar un listado con los requerimientos usando el DatabaseEnum, o se envía un @Bundle vació lo cual retornará todo el listado.
     * @return Listado con las entidades.
     */
    public ArrayList<T> listEntities(Bundle args) {
        ArrayList<T> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            if (args == null) {
                args = new Bundle();
            }
            cursor = db.query(
                    tableName,
                    null,
                    args.containsKey(DatabaseEnum.SELECTION.value) ? args.getString(DatabaseEnum.SELECTION.value) : null,
                    args.containsKey(DatabaseEnum.SELECTIONARGS.value) ? args.getStringArray(DatabaseEnum.SELECTIONARGS.value) : null,
                    args.containsKey(DatabaseEnum.GROUPBY.value) ? args.getString(DatabaseEnum.GROUPBY.value) : null,
                    args.containsKey(DatabaseEnum.HAVING.value) ? args.getString(DatabaseEnum.HAVING.value) : null,
                    args.containsKey(DatabaseEnum.SORTORDER.value) ? args.getString(DatabaseEnum.SORTORDER.value) : null,
                    args.containsKey(DatabaseEnum.LIMIT.value) ? args.getString(DatabaseEnum.LIMIT.value) : null);

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

    public T getEntity(Bundle args) {
        Cursor cursor = null;
        try {
            if (args == null) {
                args = new Bundle();
            }
            cursor = db.query(
                    tableName,
                    null,
                    args.containsKey(DatabaseEnum.SELECTION.value) ? args.getString(DatabaseEnum.SELECTION.value) : null,
                    args.containsKey(DatabaseEnum.SELECTIONARGS.value) ? args.getStringArray(DatabaseEnum.SELECTIONARGS.value) : null,
                    args.containsKey(DatabaseEnum.GROUPBY.value) ? args.getString(DatabaseEnum.GROUPBY.value) : null,
                    args.containsKey(DatabaseEnum.HAVING.value) ? args.getString(DatabaseEnum.HAVING.value) : null,
                    args.containsKey(DatabaseEnum.SORTORDER.value) ? args.getString(DatabaseEnum.SORTORDER.value) : null,
                    args.containsKey(DatabaseEnum.LIMIT.value) ? args.getString(DatabaseEnum.LIMIT.value) : null);
            if (cursor != null && cursor.moveToFirst()) {
                return transformCursorToEntity(cursor);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * @param ids arreglo de identificadores para la búsqueda de un objeto en particular. El arreglo tiene que ser declarado de manera ordenada tal cual se declaró en la entidad.
     * @return entidad buscada, dado los ids ingresados.
     */
    public T getEntity(Object[] ids) {
        Cursor cursor = null;
        try {
            cursor = db.query(tableName, null, getSelectionIdentifier(ids), null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return transformCursorToEntity(cursor);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public Cursor query(Bundle args) {

        Cursor cursor = null;
        try {
            if (args == null) {
                args = new Bundle();
            }
            cursor = db.query(tableName, null,
                    args.containsKey(DatabaseEnum.SELECTION.value) ? args.getString(DatabaseEnum.SELECTION.value) : null,
                    args.containsKey(DatabaseEnum.SELECTIONARGS.value) ? args.getStringArray(DatabaseEnum.SELECTIONARGS.value) : null,
                    args.containsKey(DatabaseEnum.GROUPBY.value) ? args.getString(DatabaseEnum.GROUPBY.value) : null,
                    args.containsKey(DatabaseEnum.HAVING.value) ? args.getString(DatabaseEnum.HAVING.value) : null,
                    args.containsKey(DatabaseEnum.SORTORDER.value) ? args.getString(DatabaseEnum.SORTORDER.value) : null,
                    args.containsKey(DatabaseEnum.LIMIT.value) ? args.getString(DatabaseEnum.LIMIT.value) : null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cursor;
    }

    public Cursor rawQuery(String query) {
        try {
            return db.rawQuery(query, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    ContentValues transformEntityToContentValues(T entity) {
        ContentValues cv = new ContentValues();
        try {
            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (lstFieldElemento.containsKey(f)) {
                    Elemento elemento = lstFieldElemento.get(f);
                    switch (elemento.elementType()) {
                        case INTEGER:
                            cv.put(elemento.columnName(), (int) f.get(entity));
                            break;
                        case LONG:
                            cv.put(elemento.columnName(), (long) f.get(entity));
                            break;
                        case STRING:
                            cv.put(elemento.columnName(), f.get(entity).toString());
                            break;
                        case BOOLEAN:
                            cv.put(elemento.columnName(), (boolean) f.get(entity));
                            break;
                        case DOUBLE:
                            cv.put(elemento.columnName(), (double) f.get(entity));
                            break;
                        case BLOB:
                            cv.put(elemento.columnName(), (byte[]) f.get(entity));
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cv;
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
                            f.setLong(entidad, transformCursorToLong(elemento.columnName(), cursor));
                            break;
                        case BLOB:
                            break;
                        case DOUBLE:
                            f.setDouble(entidad, transformCursorToDouble(elemento.columnName(), cursor));
                            break;
                        case BOOLEAN:
                            f.setBoolean(entidad, transformCursorToBoolean(elemento.columnName(), cursor));
                            break;
                        case STRING:
                            f.set(entidad, transformCursorToString(elemento.columnName(), cursor));
                            break;
                        case INTEGER:
                            f.setInt(entidad, transformCursorToInt(elemento.columnName(), cursor));
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

    private double transformCursorToDouble(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? 0d : cursor.getDouble(lstColumnIndex.get(name));
    }

    private long transformCursorToLong(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? 0l : cursor.getLong(lstColumnIndex.get(name));
    }

    private String transformCursorToString(String name, Cursor cursor) {
        return cursor.isNull(lstColumnIndex.get(name)) ? "" : cursor.getString(lstColumnIndex.get(name));
    }

    private boolean transformCursorToBoolean(String name, Cursor cursor) {
        return transformCursorToInt(name, cursor) == 1;
    }


    /**
     * @deprecated Se optimizó los tiempos de respuesta de la conversión, dado que la búsqueda getColumnIndex consumo mayor cantidad de tiempo en memoria
     */
    public static class CursorUtils {
        public static int transformCursorToInt(String name, Cursor cursor) {
            return cursor.isNull(cursor.getColumnIndex(name)) ? 0 : cursor.getInt(cursor.getColumnIndex(name));
        }

        public static double transformCursorToDouble(String name, Cursor cursor) {
            return cursor.isNull(cursor.getColumnIndex(name)) ? 0d : cursor.getDouble(cursor.getColumnIndex(name));
        }

        public static long transformCursorToLong(String name, Cursor cursor) {
            return cursor.isNull(cursor.getColumnIndex(name)) ? 0l : cursor.getLong(cursor.getColumnIndex(name));
        }

        public static String transformCursorToString(String name, Cursor cursor) {
            return cursor.isNull(cursor.getColumnIndex(name)) ? "" : cursor.getString(cursor.getColumnIndex(name));
        }

        public static boolean transformCursorToBoolean(String name, Cursor cursor) {
            return transformCursorToInt(name, cursor) == 1;
        }
    }
}
