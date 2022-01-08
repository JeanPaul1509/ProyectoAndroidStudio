package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText txtUsuario, txtPassword;
    Button btnIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignarReferencias();
    }

    private void asignarReferencias() {
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario, password;
                usuario = txtUsuario.getText().toString();
                password = txtPassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty()){
                        login();
                }else{
                    Toast.makeText(MainActivity.this, "Los campos no pueden quedar vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void  login(){
        String url = "http://jptalancha.atwebpages.com/index.php/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("=====>>","entro");

                try{
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length()==0){
                        Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        txtUsuario.setText("");
                        txtPassword.setText("");
                    }else{
                        List<String> items = new ArrayList<>();
                        for(int  i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            items.add(object.getString("nombres"));
                            items.add(object.getString("paterno"));
                            items.add(object.getString("materno"));
                        }
                        guardarPreferencias(items.get(0), items.get(1),items.get(2));
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                    }
                }catch (JSONException e){
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("usuario", txtUsuario.getText().toString());
                parametros.put("password", txtPassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(String nombre, String paterno, String materno){
        SharedPreferences preferences = getSharedPreferences("preferenciasUsuario", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("u_nombres", nombre);
        editor.putString("u_paterno", paterno);
        editor.putString("u_materno", materno);
        editor.commit();
    }

}