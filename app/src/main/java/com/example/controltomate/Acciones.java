package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acciones extends AppCompatActivity {
    private Button btnActivarBomba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);
        btnActivarBomba = findViewById(R.id.btnActivarBomba);

        // Configurar el evento clic del botón
        btnActivarBomba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HTTPCommunication.PostRequest(new String[]{"codigo"}, new String[]{"1"},"registrarAccion.php",getApplicationContext(),"La acción se ha enviado","Ya has enviado esta acción");
            }
        });
    }
}