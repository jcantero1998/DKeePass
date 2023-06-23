package com.dani_duran.seccion_042_tabs.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dani_duran.seccion_042_tabs.Model.Registro;
import com.dani_duran.seccion_042_tabs.R;

public class PassFragment extends Fragment {

    private EditText editTextUsuario;
    private EditText editTextContraseña;
    private EditText editTextUrl;
    private EditText editTextComentarios;

    public PassFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pass, container, false);

        editTextUsuario = (EditText) view.findViewById(R.id.editTextUsuario);
        editTextContraseña = (EditText) view.findViewById(R.id.editTextContraseña);
        editTextUrl = (EditText) view.findViewById(R.id.editTextUrl);
        editTextComentarios = (EditText) view.findViewById(R.id.editTextComentarios);


        return view;
    }

    public void renderRegistro (Registro registro) {

        editTextUsuario.setText(registro.getUsuario());
        editTextContraseña.setText(registro.getContraseña());
        editTextUrl.setText(registro.getUrl());
        editTextComentarios.setText(registro.getComentarios());

    }
}
