package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    TextView txtUsuario;
    Button btnCerrar, btnDireccion, btnEmpresa,btnCategoria,btnLibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        asignarPreferencias();
    }

    private void asignarPreferencias(){
        txtUsuario = findViewById(R.id.txtUsuario);
        btnCerrar = findViewById(R.id.btnCerrar);
        btnDireccion = findViewById(R.id.btnDireccion);
        btnEmpresa = findViewById(R.id.btnEmpresa);
        btnCategoria = findViewById(R.id.btnCategoria);
        btnLibro = findViewById(R.id.btnLibro);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Limpiar las Preferencias
                SharedPreferences preferences = getSharedPreferences("preferenciasUsuario", MODE_PRIVATE);
                preferences.edit().clear().commit();

                //cargar el activity Login
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        btnDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //cargar el activity Login
                Intent intent = new Intent(getApplicationContext(), Mapa.class);
                startActivity(intent);

            }
        });

        btnEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //cargar el activity Login
                Intent intent = new Intent(getApplicationContext(), Tienda.class);
                startActivity(intent);

            }
        });

        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //cargar el activity Login
                Intent intent = new Intent(getApplicationContext(), ListarCategoria.class);
                startActivity(intent);

            }
        });
        btnLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //cargar el activity Login
                Intent intent = new Intent(getApplicationContext(), ListaLibros.class);
                startActivity(intent);

            }
        });
        recuperarPreferencias();
    }

    private void recuperarPreferencias(){
        String cadena = "";
        SharedPreferences preferences = getSharedPreferences("preferenciasUsuario", MODE_PRIVATE);
        cadena += preferences.getString("u_nombres", "");
        cadena += " ";
        cadena += preferences.getString("u_paterno", "");
        cadena += " ";
        cadena += preferences.getString("u_materno", "");
        txtUsuario.setText(cadena);
    }
}