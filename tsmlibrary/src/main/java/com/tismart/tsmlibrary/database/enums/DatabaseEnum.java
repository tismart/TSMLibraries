package com.tismart.tsmlibrary.database.enums;

/**
 * Created by Luis Miguel on 23/01/2015.
 *
 * Enum para los componentes genericos usados en toda consulta o transacción en bd.
 */
@SuppressWarnings("unused")
public enum DatabaseEnum {
    SELECTION("Selection"), SELECTIONARGS("SelectionArgs"), SORTORDER("SortOrder"), LIMIT("Limit"), DISTINCT("Distinct"), FLAG("Flag"), GROUPBY("GroupBy"), HAVING("Having");

    public final String value;

    DatabaseEnum(String value) {
        this.value = value;
    }
}
