package com.vfe.vinuezafloresexamen1chds;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class Figuras extends AppCompatActivity {

    private TextView textViewRespuesta;
    private Spinner spinner;
    private EditText num1, num2, num3, num4, num5;
    private Button btnEnviar;
    private String opcionSeleccionada;
    private String urlBase = "http://10.10.29.68:3000/";
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figuras);

        // Vincular elementos de la interfaz
        textViewRespuesta = findViewById(R.id.lbl_HM);
        spinner = findViewById(R.id.spinner_cat);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        btnEnviar = findViewById(R.id.btnEnviar);

        imageView = findViewById(R.id.imageView);

        // Configurar adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Configurar selección del Spinner
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
            case "Rectángulo":
                imageView.setImageResource(R.drawable.rectangulo);
                num1.setVisibility(View.VISIBLE); // Base
                num1.setHint("Base");
                num2.setVisibility(View.VISIBLE); // Altura
                num2.setHint("Altura");
                num3.setVisibility(View.GONE);
                num4.setVisibility(View.GONE);
                num5.setVisibility(View.GONE);
                break;
            case "Trapecio":
                imageView.setImageResource(R.drawable.trapecio);
                num1.setVisibility(View.VISIBLE); // Base mayor
                num1.setHint("Base mayor");
                num2.setVisibility(View.VISIBLE); // Base menor
                num2.setHint("Base menor");
                num3.setVisibility(View.VISIBLE); // Altura
                num3.setHint("Altura");
                num4.setVisibility(View.VISIBLE); // Lado1
                num4.setHint("Lado1");
                num5.setVisibility(View.VISIBLE); // Lado2
                num5.setHint("Lado2");
                break;
            case "Triángulo":
                imageView.setImageResource(R.drawable.triangulo);
                num1.setVisibility(View.VISIBLE); // Base
                num1.setHint("Base");
                num2.setVisibility(View.VISIBLE); // Altura
                num2.setHint("Altura");
                num3.setVisibility(View.VISIBLE); // Lado1
                num3.setHint("Lado1");
                num4.setVisibility(View.VISIBLE); // Lado2
                num4.setHint("Lado2");
                num5.setVisibility(View.GONE);
                break;
            default:
                num1.setVisibility(View.GONE);
                num2.setVisibility(View.GONE);
                num3.setVisibility(View.GONE);
                num4.setVisibility(View.GONE);
                num5.setVisibility(View.GONE);
        }
    }

    private void enviarDatos() {
        try {
            // Validar y obtener valores
            double val1 = validarCampo(num1);
            double val2 = validarCampo(num2);
            double val3 = num3.getVisibility() == View.VISIBLE ? validarCampo(num3) : 0;
            double val4 = num4.getVisibility() == View.VISIBLE ? validarCampo(num4) : 0;
            double val5 = num5.getVisibility() == View.VISIBLE ? validarCampo(num5) : 0;

            String urlCompleta;
            switch (opcionSeleccionada) {
                case "Rectángulo":
                    urlCompleta = urlBase + "figura/rectangulo/" + val1 + "/" + val2;
                    break;
                case "Trapecio":
                    urlCompleta = urlBase + "figura/trapecio/" + val1 + "/" + val2 + "/" + val3 + "/" + val4 + "/" + val5;
                    break;
                case "Triángulo":
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

    private double validarCampo(EditText campo) {
        String texto = campo.getText().toString();
        if (texto.isEmpty()) {
            throw new NumberFormatException("Campo vacío");
        }
        return Double.parseDouble(texto);
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