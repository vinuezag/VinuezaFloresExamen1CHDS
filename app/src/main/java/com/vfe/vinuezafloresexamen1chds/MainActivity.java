package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private TextView textViewRespuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewRespuesta = findViewById(R.id.lbl_HM);

        // Obtener el botón
        Button sendRequestButton = findViewById(R.id.btn_saludar);

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ejecutar la solicitud HTTP usando Volley
                Solicitud();
            }
        });

    }

    private void Solicitud() {
        // URL de la solicitud
        String url = "http://10.10.29.68:3000/vinueza"; // Cambia esto según tu servidor

        // Crear una solicitud de tipo GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textViewRespuesta.setText("Respuesta del servidor: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Si ocurre un error, mostrar un mensaje de error en el TextView
                        textViewRespuesta.setText("Error en la solicitud: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}