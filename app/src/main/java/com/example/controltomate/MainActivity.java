package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button estadoActual, historial, acciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estadoActual=findViewById(R.id.btnEstadoActual);
        historial=findViewById(R.id.btnHistorial);
        acciones=findViewById(R.id.btnAcciones);

        estadoActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent currentState=new Intent(MainActivity.this,EstadoActual.class);
                startActivity(currentState);
            }
        });

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent history=new Intent(MainActivity.this, Historial.class);
                startActivity(history);
            }
        });

        acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actions=new Intent(MainActivity.this, Acciones.class);
                startActivity(actions);
            }
        });
    }
}