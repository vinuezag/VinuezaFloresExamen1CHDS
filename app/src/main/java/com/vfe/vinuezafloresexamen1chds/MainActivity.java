package com.vfe.vinuezafloresexamen1chds;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Button btnNombre, btnDescripcion, btnSuma, btnCalculadora, btnFiguras, btnTrinomio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNombre = findViewById(R.id.btnNombre);
        btnDescripcion = findViewById(R.id.btnDescripcion);
        btnSuma = findViewById(R.id.btnSuma);
        btnCalculadora = findViewById(R.id.btnCalculadora);
        btnFiguras = findViewById(R.id.btnFiguras);
        btnTrinomio = findViewById(R.id.btnTrinomio);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnNombre) {
                    startActivity(new Intent(MainActivity.this, Nombre.class));
                } else if (v.getId() == R.id.btnDescripcion) {
                    startActivity(new Intent(MainActivity.this, Greta.class));
                } else if (v.getId() == R.id.btnSuma) {
                    startActivity(new Intent(MainActivity.this, Suma.class));
                } else if (v.getId() == R.id.btnCalculadora) {
                    startActivity(new Intent(MainActivity.this, Calculadora.class));
                } else if (v.getId() == R.id.btnFiguras) {
                    startActivity(new Intent(MainActivity.this, Figuras.class));
                } else if (v.getId() == R.id.btnTrinomio) {
                    startActivity(new Intent(MainActivity.this, TrinomioCuadrado.class));
                }
            }

        };

        btnNombre.setOnClickListener(listener);
        btnDescripcion.setOnClickListener(listener);
        btnSuma.setOnClickListener(listener);
        btnCalculadora.setOnClickListener(listener);
        btnFiguras.setOnClickListener(listener);
        btnTrinomio.setOnClickListener(listener);

    }

}