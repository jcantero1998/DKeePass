package com.dani_duran.seccion_042_tabs.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dani_duran.seccion_042_tabs.Model.Registro;
import com.dani_duran.seccion_042_tabs.R;

import java.util.List;

public class RegistroAdapter extends BaseAdapter {
    //region propiedades
    //implementamos los métodos abstractos
    private Context context;
    private int layout;
    private List<Registro> registros;
    //endregion

    //region constuctor
    public RegistroAdapter(Context context, int layout, List<Registro> registros) {
        this.context = context;
        this.layout = layout;
        this.registros = registros;
    }
    //endregion

    //region metodos
    // Le dice al activity cuántas veces hay que iterar sobre un listview
    @Override
    public int getCount() {
        return this.registros.size();
    }

    // Para obtener un item, me devuelve el item de la posicion
    @Override
    public Object getItem(int position) {
        return this.registros.get(position);
    }

    //Para obtener el id de un item
    @Override
    public long getItemId(int id) {
        return id;
    }

    // Metodo donde se dibuja lo que queremos hacer (convertView es la lista de vistas que se va a dibujar)
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista que vamos a inflar
        View v = convertView;
        if (convertView ==null){
            // Usamos la clase LayoutInflater que se obtiene de un método de la misma clase pasándole u contexto
            // Inflamos la vista que nos hallegado con el layout personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            // Le indicamos el Layout que hemos creado antes
            v = layoutInflater.inflate(R.layout.list_registro_view, null);
        }

        // Nos traemos el valor de la posición
        String usuario = registros.get(position).getUsuario();
        String url = registros.get(position).getUrl();

        // Rellenar el textView del Layout
        TextView textViewUsuario = (TextView) v.findViewById(R.id.textViewUsuario);
        textViewUsuario.setText(usuario);

        TextView textViewUrl = (TextView) v.findViewById(R.id.textViewUrl);
        textViewUrl.setText(url);

        // Devolvemos la vista inflada y modificada
        return v;
    }
}

