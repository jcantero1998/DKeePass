package com.dani_duran.seccion_042_tabs.Databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper
{
    //    String crearRegistros = "CREATE TABLE registros(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario text,contraseña text, url text,comentarios text)";

    String crearRegistros = "CREATE TABLE registros(codigo INTEGER PRIMARY KEY AUTOINCREMENT, usuario text,contraseña text, url text,comentarios text)";
    String crearContrasena = "CREATE TABLE contrasena(contrasena text)";
    String añadirContraseña = "INSERT INTO contrasena(contrasena) VALUES('DamKePa')";

    public BaseDatos(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(crearRegistros);
        db.execSQL(crearContrasena);
        db.execSQL(añadirContraseña);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int versionAnt,int versionNue)
    {
        db.execSQL("DROP TABLE IF EXISTS registros");
        db.execSQL(crearRegistros);
        db.execSQL("DROP TABLE IF EXISTS contrasena");
        db.execSQL(crearContrasena);
    }
}