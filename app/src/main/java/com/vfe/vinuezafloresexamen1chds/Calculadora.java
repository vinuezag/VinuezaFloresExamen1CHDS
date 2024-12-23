package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Calculadora extends AppCompatActivity {

    private TextView textViewRespuesta;
    private EditText num1;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        textViewRespuesta = findViewById(R.id.lbl_HM);
        num1 = findViewById(R.id.num1);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarSolicitud();
            }
        });

    }

    private void realizarSolicitud() {
        // Realizar solicitud GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.10.29.68:3000/suma/"+ num1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textViewRespuesta.setText("Respuesta:\n" + response);
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