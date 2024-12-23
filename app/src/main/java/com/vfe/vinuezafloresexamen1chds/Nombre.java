package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;

public class Nombre extends AppCompatActivity {

    private TextView textViewRespuesta;
    private Button btnEnviar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre);

        textViewRespuesta = findViewById(R.id.lbl_HM);
        btnEnviar = findViewById(R.id.btnEnviar);
        imageView = findViewById(R.id.imageView);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarSolicitud();
            }
        });

    }

    private void realizarSolicitud() {
        // Realizar solicitud GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.10.29.68:3000/nombre",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Convertir la respuesta en un JSONArray
                            JSONArray jsonArray = new JSONArray(response);
                            StringBuilder nombres = new StringBuilder();

                            // Iterar sobre el JSON y extraer los nombres
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objeto = jsonArray.getJSONObject(i);
                                nombres.append(objeto.getString("nombre")).append("\n");
                            }

                            // Mostrar solo los nombres en el TextView
                            textViewRespuesta.setText("Nombres:\n" + nombres.toString());
                        } catch (Exception e) {
                            // Manejo de errores
                            textViewRespuesta.setText("Error al procesar la respuesta: " + e.getMessage());
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
}