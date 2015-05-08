package com.tismart.myapplication.daos;

import com.tismart.myapplication.entities.Entidad;
import com.tismart.tsmlibrary.database.AbstractDAO;

/**
 * Created by luis.burgos on 07/05/2015.
 *
 */
public class EntidadDAO extends AbstractDAO<Entidad> {

    public EntidadDAO() {
        super(Entidad.class);
    }
}
