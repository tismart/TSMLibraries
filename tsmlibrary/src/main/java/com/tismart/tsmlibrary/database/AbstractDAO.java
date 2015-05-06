package com.tismart.tsmlibrary.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tismart.tsmlibrary.database.annotations.Elemento;
import com.tismart.tsmlibrary.database.annotations.Entidad;
import com.tismart.tsmlibrary.database.enums.TipoElemento;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class AbstractDAO<T> {

    final private String tableName;
    final private Class<T> clase;
    final private SQLiteDatabase db = DatabaseInstance.getInstance().getDatabase();
    private final ArrayList<Field> lstPrimaryFields;

    protected AbstractDAO(Class<T> entidad) {
        Entidad annotation = entidad.getAnnotation(Entidad.class);
        tableName = annotation.tableName().length() > 0 ? annotation.tableName() : entidad.getSimpleName();
        clase = entidad;

        lstPrimaryFields = new ArrayList<>();
        for (Field f : clase.getDeclaredFields()) {
            f.setAccessible(true);
            Elemento elemento = f.getAnnotation(Elemento.class);
            if (elemento != null && elemento.isPrimary()) {
                lstPrimaryFields.add(f);
            }
        }
    }

    public ArrayList<T> listEntities() {
        ArrayList<T> lst = new ArrayList<>();
        Cursor cursor;
        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    lst.add(transformCursorToEntity(cursor));
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lst;
    }

    public long insertEntity(T entidad) throws DatabaseModificationException {
        return insertarEntidad(transformEntityToContentValues(entidad));
    }

    private long insertarEntidad(ContentValues cv) throws DatabaseModificationException {
        long row_id = db.insert(tableName, null, cv);
        if (row_id == -1) {
            throw new DatabaseModificationException();
        }
        return row_id;
    }

    public int updateEntity(T entidad) throws IllegalAccessException, DatabaseModificationException {
        ContentValues cv = transformEntityToContentValues(entidad);
        return actualizarEntidad(cv, getSelectionIdentifier(entidad, cv));
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

    private String getSelectionIdentifier(T entidad, ContentValues cv) throws IllegalAccessException {
        Elemento elemento;
        String mensaje = "";
        int cont = 0;
        for (Field field : lstPrimaryFields) {
            elemento = field.getAnnotation(Elemento.class);
            mensaje = elemento.columnName();
            cv.remove(elemento.columnName());
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

    private String getSelectionIdentifier(T entidad) throws IllegalAccessException {
        Elemento elemento;
        String mensaje = "";
        int cont = 0;
        for (Field field : lstPrimaryFields) {
            elemento = field.getAnnotation(Elemento.class);
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
            elemento = field.getAnnotation(Elemento.class);
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


    public ArrayList<T> listEntities(String[] columnNames, String selection, String orderBy) {
        ArrayList<T> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(tableName, null, selection, null, null, null, orderBy);
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

    public T getFirstEntity() {
        Cursor cursor = null;
        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
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

    public T getFirstEntity(String selection, String orderby) {
        Cursor cursor = null;
        try {
            cursor = db.query(tableName, null, selection, null, null, null, orderby);
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

    public Cursor query(String[] columnNames, String selection, String orderBy) {

        Cursor cursor = null;
        try {
            cursor = db.query(tableName, columnNames, selection, null, null, null, orderBy);
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
        String columnName;
        try {
            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Elemento.class)) {
                    Elemento elemento = f.getAnnotation(Elemento.class);
                    columnName = elemento.columnName().length() > 0 ? elemento.columnName() : f.getName();
                    switch (elemento.elementType()) {
                        case INTEGER:
                            cv.put(columnName, (int) f.get(entity));
                            break;
                        case LONG:
                            cv.put(columnName, (long) f.get(entity));
                            break;
                        case STRING:
                            cv.put(columnName, f.get(entity).toString());
                            break;
                        case BOOLEAN:
                            cv.put(columnName, (boolean) f.get(entity));
                            break;
                        case DOUBLE:
                            cv.put(columnName, (double) f.get(entity));
                            break;
                        case BLOB:
                            cv.put(columnName, (byte[]) f.get(entity));
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
        String columnName;
        try {
            T entidad = clase.newInstance();
            for (Field f : clase.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Elemento.class)) {
                    Elemento elemento = f.getAnnotation(Elemento.class);
                    columnName = elemento.columnName().length() > 0 ? elemento.columnName() : f.getName();
                    switch (elemento.elementType()) {
                        case LONG:
                            f.setLong(entidad, CursorUtils.transformCursorToLong(columnName, cursor));
                            break;
                        case BLOB:
                            break;
                        case DOUBLE:
                            f.setDouble(entidad, CursorUtils.transformCursorToDouble(columnName, cursor));
                            break;
                        case BOOLEAN:
                            f.setBoolean(entidad, CursorUtils.transformCursorToBoolean(columnName, cursor));
                            break;
                        case STRING:
                            f.set(entidad, CursorUtils.transformCursorToString(columnName, cursor));
                            break;
                        case INTEGER:
                            f.setInt(entidad, CursorUtils.transformCursorToInt(columnName, cursor));
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
