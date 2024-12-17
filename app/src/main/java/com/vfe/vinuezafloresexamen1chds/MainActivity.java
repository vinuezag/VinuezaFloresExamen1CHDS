package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRespuesta;
    private Spinner spinner;
    private String urlBase = "http://10.10.29.68:3000/"; // Cambia esto a tu IP local o servidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewRespuesta = findViewById(R.id.lbl_HM);
        spinner = findViewById(R.id.spinner_cat);

        // Configurar el adaptador del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Manejar la selección del Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcionSeleccionada = parent.getItemAtPosition(position).toString();
                realizarSolicitud(opcionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewRespuesta.setText("No se seleccionó ninguna opción.");
            }
        });
    }

    private void realizarSolicitud(String opcion) {
        String urlCompleta = urlBase + opcion; // URL dinámica

        // Realizar solicitud GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlCompleta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (opcion.equals("nombre")) {
                            // Manejar respuesta JSON
                            manejarJSON(response);
                        } else {
                            // Mostrar texto plano
                            textViewRespuesta.setText("Respuesta del servidor:\n" + response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewRespuesta.setText("Error en la solicitud: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola
        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void manejarJSON(String response) {
        try {
            // Parsear el JSON recibido
            JSONArray jsonArray = new JSONArray(response);
            StringBuilder resultado = new StringBuilder();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objeto = jsonArray.getJSONObject(i);
                String id = objeto.getString("id");
                String nombre = objeto.getString("nombre");

                // Concatenar cada elemento
                resultado.append("ID: ").append(id).append(", Nombre: ").append(nombre).append("\n");
            }

            // Mostrar resultado en el TextView
            textViewRespuesta.setText(resultado.toString());

        } catch (JSONException e) {
            textViewRespuesta.setText("Error al parsear JSON: " + e.getMessage());
        }
    }
}
