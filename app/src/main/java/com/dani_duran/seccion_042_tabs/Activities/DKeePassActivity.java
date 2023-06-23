package com.dani_duran.seccion_042_tabs.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
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

public class DKeePassActivity extends AppCompatActivity implements ListFragment.DataListener{

    //region Propiedades
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private static BaseDatos datos;
    private static SQLiteDatabase db;
    public static List<Registro> registros = new ArrayList<Registro>();
    public static RegistroAdapter registroAdapter;
    private AlertDialog dialog;
    AlertDialog.Builder builder;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dkee_pass);

        //region Base de datos
        //Asignamos nuestra base de datos a la variable
        datos = new BaseDatos(this, "Datos", null, 1);

        registroAdapter = new RegistroAdapter(this,R.layout.list_registro_view,registros);

        //Cargar la lista de la base de datos
        try {
            String respuesta = cargarRegistros();
            if (respuesta != "") {
                Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //endregion

        setupToolBar();
        setTabLayout();
        setViewPager();
        setListenerTabLayout(viewPager);
    }

    //region ToolBar, TabLayout y ViewPager
    private void setupToolBar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    private void setTabLayout() {
        // Instancio los objetos tabLayout y ViewPager declarados en el layout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        // Añado al tabLayout las pestañas-tabs
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Pass"));
    }

    private void setViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        // En el ViewPager tenemos que crear la clase
        // Instanciamos el PAgerAdapter, hay que tener en cuenta que haga el import
        // de nuestra clase, en vez de una del android
        // El número de tabs en vez de pasar un 3 en este caso le pasamos el getTabCount para que sea
        // más dinámico
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        // Asignamos el adaptador al ViewPager
        viewPager.setAdapter(adapter);
        // Configuramos el listener para que esté escuchando cada vez que cambiamos
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setListenerTabLayout(final ViewPager viewPager) {
        // Definimos que hay que hacer cuándo cambiemos de un tab a otro
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Cuando seleccionamos el tab
                // Toast.makeText(MainActivity.this, "Selected: " + tab.getText(),Toast.LENGTH_SHORT).show();
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Cuando el tab que está activo deja de estarlo
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Cuándo seleccionamos el mismo tab que está activo
            }
        });
    }
    //endregion

    //region Menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Manejamos el evento click en el menú de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Hacemos un switch porque comtemplamos que pueda haber más de una opción en el menú
        builder = new AlertDialog.Builder(DKeePassActivity.this);

        switch (item.getItemId()) {
            // En el caso de nuestra opción
            case R.id.newreg:
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_pass, null);
                final EditText editTextUsuario = (EditText) mView.findViewById(R.id.editTextUsuario);
                final EditText editTextContraseña = (EditText) mView.findViewById(R.id.editTextContraseña);
                final EditText editTextUrl = (EditText) mView.findViewById(R.id.editTextUrl);
                final EditText editTextComentarios = (EditText) mView.findViewById(R.id.editTextComentarios);

                Button buttonAgregar = (Button) mView.findViewById(R.id.buttonAgregar);
                buttonAgregar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextUsuario.getText().toString().equals("") || editTextContraseña.getText().toString().equals("")|| editTextUrl.getText().toString().equals("") || editTextComentarios.getText().toString().equals("")) {
                            Toast.makeText(DKeePassActivity.this,"Completa todos los campos",Toast.LENGTH_LONG).show();
                        }else {
                            viewPager.setCurrentItem(0);

                            Registro addRegistro = new Registro(editTextUsuario.getText().toString(),editTextContraseña.getText().toString(),editTextUrl.getText().toString(),editTextComentarios.getText().toString());

                            String respuesta = DKeePassActivity.agregarRegistro(addRegistro);
                            if (respuesta!=""){
                                Toast.makeText(DKeePassActivity.this,respuesta,Toast.LENGTH_LONG).show();
                            }else {
                                //Borramos los campos de los edit text
                                editTextUsuario.setText("");
                                editTextContraseña.setText("");
                                editTextUrl.setText("");
                                editTextComentarios.setText("");
                            }
                        }
                    }
                });
                builder.setView(mView);
                dialog = builder.create();
                dialog.show();

                return true;
            case R.id.changepass:
                View mView2 = getLayoutInflater().inflate(R.layout.dialog_change_activity_pass, null);
                final EditText editTextPassActual = (EditText) mView2.findViewById(R.id.editTextPassActual);
                final EditText editTextNuevaPass = (EditText) mView2.findViewById(R.id.editTextNuevaPass);

                Button buttonModificar = (Button) mView2.findViewById(R.id.buttonModificar);
                buttonModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextPassActual.getText().toString().equals("") || editTextNuevaPass.getText().toString().equals("")) {
                            Toast.makeText(DKeePassActivity.this,"Completa todos los campos",Toast.LENGTH_LONG).show();
                        }else if (!editTextPassActual.getText().toString().equals(MainActivity.contraseña)) {
                            Toast.makeText(DKeePassActivity.this,"Contraseña actual incorrecta",Toast.LENGTH_LONG).show();
                        }else{
                            MainActivity.modificarContraseña(editTextNuevaPass.getText().toString());
                            editTextPassActual.setText("");
                            editTextNuevaPass.setText("");
                        }
                    }
                });
                builder.setView(mView2);
                dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Inflamos el layout de context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        // Antes de inflarlo, añadimos un título al menú contextual
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(registros.get(info.position).getUsuario());
        inflater.inflate(R.menu.context_menu, menu);
    }

    // Manejamos eventos click del menú contextual
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.edit_item:
                builder = new AlertDialog.Builder(DKeePassActivity.this);

                View mView = getLayoutInflater().inflate(R.layout.dialog_change_pass, null);
                final EditText editTextUsuario = (EditText) mView.findViewById(R.id.editTextUsuario);
                final EditText editTextContraseña = (EditText) mView.findViewById(R.id.editTextContraseña);
                final EditText editTextUrl = (EditText) mView.findViewById(R.id.editTextUrl);
                final EditText editTextComentarios = (EditText) mView.findViewById(R.id.editTextComentarios);

                final int code = registros.get(info.position).getCodigo();

                editTextUsuario.setText(registros.get(info.position).getUsuario());
                editTextContraseña.setText(registros.get(info.position).getContraseña());
                editTextUrl.setText(registros.get(info.position).getUrl());
                editTextComentarios.setText(registros.get(info.position).getComentarios());

                Button buttonModificar = (Button) mView.findViewById(R.id.buttonModificar);
                buttonModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editTextUsuario.getText().toString().equals("") || editTextContraseña.getText().toString().equals("")|| editTextUrl.getText().toString().equals("") || editTextComentarios.getText().toString().equals("")) {
                            Toast.makeText(DKeePassActivity.this,"Completa todos los campos",Toast.LENGTH_LONG).show();
                        }else {
                            //eliminarRegistro(code);
                            //Registro addRegistro = new Registro(editTextUsuario.getText().toString(),editTextContraseña.getText().toString(),editTextUrl.getText().toString(),editTextComentarios.getText().toString());
                            //String respuesta = DKeePassActivity.agregarRegistro(addRegistro);

                            Registro updateRegistro = new Registro(editTextUsuario.getText().toString(),editTextContraseña.getText().toString(),editTextUrl.getText().toString(),editTextComentarios.getText().toString());
                            String respuesta = DKeePassActivity.modificarRegistro(code,updateRegistro);

                            if (respuesta!=""){
                                Toast.makeText(DKeePassActivity.this,respuesta,Toast.LENGTH_LONG).show();
                            }else {
                                //Borramos los campos de los edit text
                                editTextUsuario.setText("");
                                editTextContraseña.setText("");
                                editTextUrl.setText("");
                                editTextComentarios.setText("");
                            }
                        }
                    }
                });
                builder.setView(mView);
                dialog = builder.create();
                dialog.show();
                return true;
            case R.id.delete_item:
                eliminarRegistro(registros.get(info.position).getCodigo());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //endregion

    //region crud + cargar
    public static String cargarRegistros() {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            //Vaciamos la lista
            registros.clear();

            //Volvemos a cargar la lista
            Cursor fila = db.rawQuery("select * from registros", null);
            if (fila.moveToFirst()) {
                do {
                    registros.add(new Registro(fila.getInt(0),fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
                } while (fila.moveToNext());
            }
            db.close();
            return "";
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }

    public static String agregarRegistro(Registro addRegistro) {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            ContentValues registro = new ContentValues();

            registro.put("usuario", addRegistro.getUsuario());
            registro.put("contraseña", addRegistro.getContraseña());
            registro.put("url", addRegistro.getUrl());
            registro.put("comentarios", addRegistro.getComentarios());

            if (db.insert("registros", null, registro) == -1) {
                db.close();
                return "Error al añadir el Registro";
            } else {
                //Cargamos la lista de personas
                cargarRegistros();
                //Notificamos el cambio a los adapters para que aparezca la persona
                registroAdapter.notifyDataSetChanged();
                db.close();
                return "";
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }
    }

    public static String modificarRegistro(int codigo, Registro updateRegistro) {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            ContentValues contentValues = new ContentValues();

            contentValues.put("usuario", updateRegistro.getUsuario());
            contentValues.put("contraseña", updateRegistro.getContraseña());
            contentValues.put("url", updateRegistro.getUrl());
            contentValues.put("comentarios", updateRegistro.getComentarios());

            int cantidadModificados = db.update("registros",contentValues,"codigo=?",new String[]{String.valueOf(codigo)});
            if (cantidadModificados != 1) {
                db.close();
                return "No ha sido posible modificar el registro";
            } else {
                //Volvemos a cargar la lista
                cargarRegistros();
                //Notificamos el cambio a los adapters
                registroAdapter.notifyDataSetChanged();
                db.close();
                return "";
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }

    }

    public static String eliminarRegistro(int codigo) {
        //Abrimos la base de datos en modo lectura y escritura
        db = datos.getWritableDatabase();

        if (db != null) {
            int cantidadBorrados = db.delete("registros", "codigo=?", new String[]{String.valueOf(codigo)});
            if (cantidadBorrados != 1) {
                db.close();
                return "No ha sido posible eliminar el registro";
            } else {
                //Volvemos a cargar la lista
                cargarRegistros();
                //Notificamos el cambio a los adapters
                registroAdapter.notifyDataSetChanged();
                db.close();
                return "";
            }
        } else {
            db.close();
            return "Error al acceder a la base de datos";
        }

    }
    //endregion

    @Override
    public void sendRegistro(Registro registro) {
        viewPager.setCurrentItem(1);
        PassFragment passFragment = (PassFragment) PagerAdapter.passFragment;
        passFragment.renderRegistro(registro);
    }

}