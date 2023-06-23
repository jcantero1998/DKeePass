package com.dani_duran.seccion_042_tabs.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dani_duran.seccion_042_tabs.Activities.DKeePassActivity;
import com.dani_duran.seccion_042_tabs.Activities.MainActivity;
import com.dani_duran.seccion_042_tabs.Model.Registro;
import com.dani_duran.seccion_042_tabs.R;


public class ListFragment extends Fragment {
    private ListView listView;
    private DataListener callback;


    public ListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(DKeePassActivity.registroAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        callback.sendRegistro(DKeePassActivity.registros.get(position));

            }
        });
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Convertimos el contexto en DataListener y lo guardamos en el callback
            callback = (DataListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " DataListener mal implementado");
        }
    }

    public interface DataListener {
        void sendRegistro(Registro registro);
    }
}