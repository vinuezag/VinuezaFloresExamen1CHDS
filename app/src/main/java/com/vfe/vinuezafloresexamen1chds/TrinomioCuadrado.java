package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class TrinomioCuadrado extends AppCompatActivity {

    private TextView textViewRespuesta, uno, dos, tres, par1, par2;
    private EditText num1, num2, num3, numA, numB, sim1, sim2, sim3;
    private String opcionSeleccionada;
    private Spinner spinner;
    private Button btnEnviar;

    private String urlBase = "http://10.10.29.68:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trinomio_cuadrado);

        // Vincular elementos de la interfaz
        textViewRespuesta = findViewById(R.id.lbl_HM);
        spinner = findViewById(R.id.spinner);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        numA = findViewById(R.id.numA);
        numB = findViewById(R.id.numB);
        sim1 = findViewById(R.id.sim1);
        sim2 = findViewById(R.id.sim2);
        sim3 = findViewById(R.id.sim3);

        uno = findViewById(R.id.uno);
        dos = findViewById(R.id.dos);
        tres = findViewById(R.id.tres);
        par1 = findViewById(R.id.par1);
        par2 = findViewById(R.id.par2);

        btnEnviar = findViewById(R.id.btnEnviar);

        // Configurar adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.calculo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                configurarVisibilidadCampos(opcionSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewRespuesta.setText("No se seleccionó ninguna opción.");
            }
        });

        // Configurar botón Enviar
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });
    }

    private void configurarVisibilidadCampos(String opcion) {
        // Configura la visibilidad de los campos según la opción seleccionada
        switch (opcion) {
            case "Factorizar":
                num1.setVisibility(View.VISIBLE);
                num2.setVisibility(View.VISIBLE);
                num3.setVisibility(View.VISIBLE);
                numA.setVisibility(View.GONE);
                numB.setVisibility(View.GONE);
                sim1.setVisibility(View.VISIBLE);
                sim2.setVisibility(View.VISIBLE);
                sim3.setVisibility(View.GONE);

                uno.setVisibility(View.VISIBLE);
                dos.setVisibility(View.VISIBLE);
                tres.setVisibility(View.GONE);
                par1.setVisibility(View.GONE);
                par2.setVisibility(View.GONE);

                break;
            case "Expandir":
                num1.setVisibility(View.GONE);
                num2.setVisibility(View.GONE);
                num3.setVisibility(View.GONE);
                numA.setVisibility(View.VISIBLE);
                numB.setVisibility(View.VISIBLE);
                sim1.setVisibility(View.GONE);
                sim2.setVisibility(View.GONE);
                sim3.setVisibility(View.VISIBLE);

                uno.setVisibility(View.GONE);
                dos.setVisibility(View.GONE);
                tres.setVisibility(View.VISIBLE);
                par1.setVisibility(View.VISIBLE);
                par2.setVisibility(View.VISIBLE);
                break;
            default:
                num1.setVisibility(View.VISIBLE);
                num2.setVisibility(View.VISIBLE);
                num3.setVisibility(View.VISIBLE);
                numA.setVisibility(View.VISIBLE);
                numB.setVisibility(View.VISIBLE);
                sim1.setVisibility(View.VISIBLE);
                sim2.setVisibility(View.VISIBLE);
                sim3.setVisibility(View.VISIBLE);

                uno.setVisibility(View.VISIBLE);
                dos.setVisibility(View.VISIBLE);
                tres.setVisibility(View.VISIBLE);
                par1.setVisibility(View.VISIBLE);
                par2.setVisibility(View.VISIBLE);
        }
    }

    private void enviarDatos() {
        try {

            String urlCompleta;
            switch (opcionSeleccionada) {
                case "Factorizar":
                    urlCompleta = urlBase + "trinomio/Factorizar/" + num1 + "/" + sim1 + "/" + num3;
                    break;
                case "Expandir":
                    urlCompleta = urlBase + "trinomio/Expandir/" + numA + "/" + sim3 + "/" + numB;
                    break;
                default:
                    textViewRespuesta.setText("Opción no válida.");
                    return;
            }

            realizarSolicitud(urlCompleta);

        } catch (NumberFormatException e) {
            textViewRespuesta.setText("Por favor ingresa valores válidos en los campos visibles.");
        }
    }
    private void realizarSolicitud(String urlCompleta) {
        // Realizar solicitud GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlCompleta,
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