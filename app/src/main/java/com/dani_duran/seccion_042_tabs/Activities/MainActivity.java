package com.dani_duran.seccion_042_tabs.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.dani_duran.seccion_042_tabs.Adapters.PagerAdapter;
import com.dani_duran.seccion_042_tabs.Adapters.RegistroAdapter;
import com.dani_duran.seccion_042_tabs.Databases.BaseDatos;
import com.dani_duran.seccion_042_tabs.Fragments.ListFragment;
import com.dani_duran.seccion_042_tabs.Fragments.PassFragment;
import com.dani_duran.seccion_042_tabs.Model.Registro;
import com.dani_duran.seccion_042_tabs.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    public static String contraseña;
    private static BaseDatos datos;
    private static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos nuestra base de datos a la variable
        datos = new BaseDatos(this, "Datos", null, 1);

        cargarContraseña();

        //if (contraseña==null |contraseña == ""){
        //    agregarRegistro("DamKePa");
        //}

        //region Dialog SignIn
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_signin, null);
        final EditText etPassword = (EditText) mView.findViewById(R.id.password);
        Button buttonSignin = (Button) mView.findViewById(R.id.buttonSignin);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals(contraseña)) {
                    Intent intent = new Intent(getApplicationContext(), DKeePassActivity.class);
                    startActivity(intent);
                }
            }
        });

        builder.setView(mView);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        //endregion
    }

    public static String agregarRegistro(String contraseña) {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            ContentValues registro = new ContentValues();

            registro.put("contrasena", contraseña);

            if (db.insert("contrasena", null, registro) == -1) {
                db.close();
                return "Error al añadir el Registro";
            } else {

                db.close();
                return "";
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }

    public static String cargarContraseña() {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            //Volvemos a cargar la lista
            Cursor fila = db.rawQuery("select * from contrasena", null);
            if (fila.moveToFirst()) {
                MainActivity.contraseña = fila.getString(0);
                db.close();
            }
            return "";
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }

    public static String modificarContraseña(String contraseñaACambiar) {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {

            //Volvemos a cargar la lista
            //db.delete("contrasena", null, null);

            db.execSQL("delete from contrasena");

            agregarRegistro(contraseñaACambiar);
            cargarContraseña();
            return "";
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }
}