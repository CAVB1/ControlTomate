package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_ID = "MyChannel";
Button estadoActual, historial, acciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        Intent intent = new Intent(this, Background.class);
        if (!ServiceUtils.isMyServiceRunning(this, Background.class)) {
            startService(intent);
        } else {
            // El servicio ya está corriendo, no es necesario iniciarlo nuevamente
        }
        //startService(intent);

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "My Channel Name",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}