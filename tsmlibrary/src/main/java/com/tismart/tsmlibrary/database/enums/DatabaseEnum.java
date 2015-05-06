package com.tismart.tsmlibrary.database.enums;

/**
 * Created by Luis Miguel on 23/01/2015.
 * <p/>
 * Enum para los componentes genericos usados en toda consulta o transacción en bd.
 */
@SuppressWarnings("unused")
public enum DatabaseEnum {
    PROJECTION("Projection"), SELECTION("Selection"), SELECTIONARGS("SelectionArgs"), SORTORDER("SortOrder"), LIMIT("Limit"), DISTINCT("Distinct"), FLAG("Flag");

    final private String valor;

    DatabaseEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }
}
