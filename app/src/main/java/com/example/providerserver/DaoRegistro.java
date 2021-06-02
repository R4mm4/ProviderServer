package com.example.providerserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DaoRegistro {
    SQLiteDatabase _sqlSqLiteDatabase;

    public DaoRegistro(Context ctx) {
        BD miDB = new BD(ctx);
        _sqlSqLiteDatabase =
                miDB.getWritableDatabase();
    }

    public long insert(Contacto contacto) {
        ContentValues cv =
                new ContentValues();
        cv.put(BD.COLUMNS_CONTACTOS[1],
                contacto.getUsuario());
        cv.put(BD.COLUMNS_CONTACTOS[2],
                contacto.getEmail());
        cv.put(BD.COLUMNS_CONTACTOS[3],
                contacto.getTel());
        cv.put(BD.COLUMNS_CONTACTOS[4],
                contacto.getFechaNac());
        return _sqlSqLiteDatabase.insert(
                BD.TABLE_NAME_CONTACTOS,
                null,
                cv);
    }

    public long update(Contacto contacto, String id){
        ContentValues cv =
                new ContentValues();
        cv.put(BD.COLUMNS_CONTACTOS[1],
                contacto.getUsuario());
        cv.put(BD.COLUMNS_CONTACTOS[2],
                contacto.getEmail());
        cv.put(BD.COLUMNS_CONTACTOS[3],
                contacto.getTel());
        cv.put(BD.COLUMNS_CONTACTOS[4],
                contacto.getFechaNac());
        return _sqlSqLiteDatabase.update(BD.TABLE_NAME_CONTACTOS,cv,"_id=?",new String[]{id});
    }

    public int delete(String id){
        return _sqlSqLiteDatabase.delete(BD.TABLE_NAME_CONTACTOS,"_id=?",new String[]{id});
    }

    public Cursor filter(String inputText, String filterColumn) throws SQLException {
        Cursor row = null;
        String query = "SELECT * FROM " + BD.TABLE_NAME_CONTACTOS;
        if (inputText == null || inputText.length() == 0) {
            row = _sqlSqLiteDatabase.rawQuery(query, null);
        } else {
            query = "SELECT * FROM " + BD.TABLE_NAME_CONTACTOS + " WHERE " + filterColumn + " like '%" + inputText + "%'";
            row = _sqlSqLiteDatabase.rawQuery(query, null);
        }
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    public List<Contacto> getAll(){
        List<Contacto> lst = null;

        Cursor c = _sqlSqLiteDatabase.
                query(BD.TABLE_NAME_CONTACTOS,
                        BD.COLUMNS_CONTACTOS,
                        null,
                        null,
                        null,
                        null,null);

        if ( c.moveToFirst()){
            lst = new ArrayList<Contacto>();
            do {
                Contacto ctc = new
                        Contacto(c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4)

                );
                lst.add(ctc);
            }while(c.moveToNext());
        }
        return lst;
    }

    public Cursor getAllCursor(){

        Cursor c = _sqlSqLiteDatabase.
                query(BD.TABLE_NAME_CONTACTOS,
                        BD.COLUMNS_CONTACTOS,
                        null,
                        null,
                        null,
                        null,null);

        return c;
    }
}
