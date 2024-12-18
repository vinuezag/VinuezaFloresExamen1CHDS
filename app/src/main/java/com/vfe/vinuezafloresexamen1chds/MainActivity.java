package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText num1, num2, num3, num4, num5;
    private Button btnEnviar;
    private String opcionSeleccionada;
    private String urlBase = "http://10.10.13.47:3000/"; // Cambia esto a tu IP local o servidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewRespuesta = findViewById(R.id.lbl_HM);
        spinner = findViewById(R.id.spinner_cat);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        btnEnviar = findViewById(R.id.btnEnviar);

        // Configurar el adaptador del Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Manejar la selección del Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 opcionSeleccionada = parent.getItemAtPosition(position).toString();
                if (opcionSeleccionada.equals("rectangulo")) {
                    // Habilita los campos necesarios para el rectángulo
                    num1.setVisibility(View.VISIBLE); // Base
                    num2.setVisibility(View.VISIBLE); // Altura
                    num3.setVisibility(View.GONE);
                    num4.setVisibility(View.GONE);
                    num5.setVisibility(View.GONE);
                } else if (opcionSeleccionada.equals("trapecio")) {
                    // Habilita los campos necesarios para el trapecio
                    num1.setVisibility(View.VISIBLE); // Base mayor
                    num2.setVisibility(View.VISIBLE); // Base menor
                    num3.setVisibility(View.VISIBLE); // Altura
                    num4.setVisibility(View.VISIBLE); // Lado1
                    num5.setVisibility(View.VISIBLE); // Lado2
                } else if (opcionSeleccionada.equals("triangulo")) {
                    // Habilita los campos necesarios para el triángulo
                    num1.setVisibility(View.VISIBLE); // Base
                    num2.setVisibility(View.VISIBLE); // Altura
                    num3.setVisibility(View.VISIBLE); // Lado1
                    num4.setVisibility(View.VISIBLE); // Lado2
                    num5.setVisibility(View.GONE);
                }
                realizarSolicitud(opcionSeleccionada);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewRespuesta.setText("No se seleccionó ninguna opción.");
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });
    }

    private void enviarDatos() {


        String urlCompleta;
        try {
            double val1 = num1.getVisibility() == View.VISIBLE ? Double.parseDouble(num1.getText().toString()) : 0;
            double val2 = num2.getVisibility() == View.VISIBLE ? Double.parseDouble(num2.getText().toString()) : 0;
            double val3 = num3.getVisibility() == View.VISIBLE ? Double.parseDouble(num3.getText().toString()) : 0;
            double val4 = num4.getVisibility() == View.VISIBLE ? Double.parseDouble(num4.getText().toString()) : 0;
            double val5 = num5.getVisibility() == View.VISIBLE ? Double.parseDouble(num5.getText().toString()) : 0;

            switch (opcionSeleccionada) {
                case "rectangulo":
                    urlCompleta = urlBase + "figura/rectangulo/" + val1 + "/" + val2;
                    break;
                case "trapecio":
                    urlCompleta = urlBase + "figura/trapecio/" + val1 + "/" + val2 + "/" + val3 + "/" + val4 + "/" + val5;
                    break;
                case "triangulo":
                    urlCompleta = urlBase + "figura/triangulo/" + val1 + "/" + val2 + "/" + val3 + "/" + val4;
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
    private void realizarSolicitud(String opcion) {
        String urlCompleta;

        double val1 = Double.parseDouble(num1.getText().toString());
        double val2 = Double.parseDouble(num2.getText().toString());
        double val3 = num3.getVisibility() == View.VISIBLE ? Double.parseDouble(num3.getText().toString()) : 0;
        double val4 = num4.getVisibility() == View.VISIBLE ? Double.parseDouble(num4.getText().toString()) : 0;
        double val5 = num5.getVisibility() == View.VISIBLE ? Double.parseDouble(num5.getText().toString()) : 0;

        // Configurar la URL basada en la opción seleccionada
        switch (opcion) {
            case "rectangulo":
                urlCompleta = urlBase + "figura/rectangulo/" + val1 + "/" + val2;
                break;
            case "trapecio":
                urlCompleta = urlBase + "figura/trapecio/" + val1 + "/" + val2 + "/" + val3 + "/" + val4 + "/" + val5;
                break;
            case "triangulo":
                urlCompleta = urlBase + "figura/triangulo/" + val1 + "/" + val2 + "/" + val3 + "/" + val4;
                break;
            default:
                textViewRespuesta.setText("Opción no válida.");
                return;
        }

        // Realizar solicitud GET con Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlCompleta,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (opcion.equals("nombre")) {
                            // Manejar respuesta JSON
                            manejarJSON(response);
                        } else if (opcion.equals("rectangulo") || opcion.equals("trapecio") || opcion.equals("triangulo")) {
                            // Manejar respuesta JSON de área y perímetro
                            textViewRespuesta.setText("Respuesta del servidor:\n" + response);
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
