package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tanque1, tanque2, humedad, ph;
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
        tanque1=findViewById(R.id.tanque1);
        tanque2=findViewById(R.id.tanque2);
        humedad=findViewById(R.id.humedad);
        ph=findViewById(R.id.ph);

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



        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(HTTPCommunication.SERVER_NAME + "getLastEstado.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject object=(response.getJSONObject(0));
                    if(object.getInt("NIVEL_AGUA")<=0){
                        tanque1.setText("Tanque 1\n Estado: Rellenar agua (0% nivel de agua)");
                    }else if (object.getInt("NIVEL_AGUA")<10){
                        tanque1.setText("Tanque 1:\n Estado: Rellenar agua (<10% nivel de agua)");

                    }else if (object.getInt("NIVEL_AGUA")<50){
                        tanque1.setText("Tanque 1:\n Estado: Se está quedando sin agua (<50% nivel de agua)");
                    }else {
                        tanque1.setText("Tanque 1:\n Estado: Niveles de agua correctos (>=50% nivel de agua)");


                    }

                    if(object.getInt("NIVEL_AGUA2")<=0){
                        tanque2.setText("Tanque 2\n Estado: Rellenar agua (0% nivel de agua)");
                    }else if (object.getInt("NIVEL_AGUA2")<10){
                        tanque2.setText("Tanque 2:\n Estado: Rellenar agua (<10% nivel de agua)");

                    }else if (object.getInt("NIVEL_AGUA2")<50){
                        tanque2.setText("Tanque 2:\n Estado: Se está quedando sin agua (<50% nivel de agua)");
                    }else {
                        tanque2.setText("Tanque 2:\n Estado: Niveles de agua correctos (>=50% nivel de agua)");
                    }

                    humedad.setText("Humedad de la planta: "+object.getString("NIVEL_HUMEDAD")+"%\nEstado: Normal");

                    ph.setText("PH de la planta: "+object.getString("NIVEL_PH")+"%");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

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