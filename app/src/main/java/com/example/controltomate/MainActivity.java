package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tanque1, tanque2, humedad, ph,statet;
    public static final String CHANNEL_ID = "MyChannel";
Button estadoActual, historial, acciones;
RelativeLayout bloque1, bloque2, bloque3, bloque4;
private ImageView imaget;
private ScrollView cont;
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
        tanque1=findViewById(R.id.ttanque1);
        tanque2=findViewById(R.id.ttanque2);
        humedad=findViewById(R.id.thumedad);
        ph=findViewById(R.id.tph);
        bloque1=findViewById(R.id.block1);
        bloque2=findViewById(R.id.block2);
        cont=findViewById(R.id.maincon);
        imaget=findViewById(R.id.imaget);
        statet=findViewById(R.id.stateText);

        imaget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(HTTPCommunication.SERVER_NAME + "getLastEstado.php", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject object=(response.getJSONObject(0));
                            if(object.getInt("NIVEL_AGUA")<=0){
                                tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                                bloque1.setBackgroundResource(R.color.rojillo);
                                bloque1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Es necesario llenar el tanque 1\nEstá vacío", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else if (object.getInt("NIVEL_AGUA")<10){
                                tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                                bloque1.setBackgroundResource(R.color.rojillo);
                                bloque1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "El tanque 1 se está quedando sin agua\nSe recomienda llenarlo pronto", Toast.LENGTH_LONG).show();
                                    }
                                });;

                            }else if (object.getInt("NIVEL_AGUA")<50){
                                tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                                bloque1.setBackgroundResource(R.color.rojillo);
                                bloque1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "El agua está por debajo del 50%", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                                tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                                bloque1.setBackgroundResource(R.color.greensh);
                                bloque1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "EL nivel de agua en el tanque 1 es correcto", Toast.LENGTH_LONG).show();
                                    }
                                });


                            }

                            if(object.getInt("NIVEL_AGUA2")<=0){
                                tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                                bloque2.setBackgroundResource(R.color.rojillo);
                                bloque2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "Es necesario llenar el tanque 2\nEstá vacío", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else if (object.getInt("NIVEL_AGUA2")<10){
                                tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                                bloque2.setBackgroundResource(R.color.rojillo);
                                bloque2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "El tanque 2 se está quedando sin agua\nSe recomienda llenarlo pronto", Toast.LENGTH_LONG).show();
                                    }
                                });;

                            }else if (object.getInt("NIVEL_AGUA2")<50){
                                tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                                bloque2.setBackgroundResource(R.color.rojillo);
                                bloque2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "El agua está por debajo del 50%", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                                tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                                bloque2.setBackgroundResource(R.color.greensh);
                                bloque2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(MainActivity.this, "EL nivel de agua en el tanque 2 es correcto", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            humedad.setText(object.getString("NIVEL_HUMEDAD")+"%");

                            ph.setText(object.getString("NIVEL_PH")+"%");

                            if((object.getInt("NIVEL_AGUA")<50&&object.getInt("NIVEL_AGUA2")<50)||object.getInt("NIVEL_AGUA")<=10||object.getInt("NIVEL_AGUA2")<=10){
                                cont.setBackgroundResource(R.drawable.orangeborder);
                                imaget.setImageResource(R.drawable.sad);
                                statet.setText("Estado\nLa planta necesita atención");

                            }else{
                                cont.setBackgroundResource(R.drawable.border);
                                imaget.setImageResource(R.drawable.japy);

                                statet.setText("Estado\nLa planta está bien");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);
            }
        });


        estadoActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent currentState=new Intent(MainActivity.this,EstadoActual.class);
                startActivity(currentState);*/
                Intent ver=new Intent(MainActivity.this, VerPlanta.class);
                startActivity(ver);
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
                        tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                        bloque1.setBackgroundResource(R.color.rojillo);
                        bloque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Es necesario llenar el tanque 1\nEstá vacío", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if (object.getInt("NIVEL_AGUA")<10){
                        tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                        bloque1.setBackgroundResource(R.color.rojillo);
                        bloque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "El tanque 1 se está quedando sin agua\nSe recomienda llenarlo pronto", Toast.LENGTH_LONG).show();
                            }
                        });;

                    }else if (object.getInt("NIVEL_AGUA")<50){
                        tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                        bloque1.setBackgroundResource(R.color.rojillo);
                        bloque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "El agua está por debajo del 50%", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        tanque1.setText(object.getString("NIVEL_AGUA")+"%");
                        bloque1.setBackgroundResource(R.color.greensh);
                        bloque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "EL nivel de agua en el tanque 1 es correcto", Toast.LENGTH_LONG).show();
                            }
                        });


                    }

                    if(object.getInt("NIVEL_AGUA2")<=0){
                        tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                        bloque2.setBackgroundResource(R.color.rojillo);
                        bloque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "Es necesario llenar el tanque 2\nEstá vacío", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if (object.getInt("NIVEL_AGUA2")<10){
                        tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                        bloque2.setBackgroundResource(R.color.rojillo);
                        bloque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "El tanque 2 se está quedando sin agua\nSe recomienda llenarlo pronto", Toast.LENGTH_LONG).show();
                            }
                        });;

                    }else if (object.getInt("NIVEL_AGUA2")<50){
                        tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                        bloque2.setBackgroundResource(R.color.rojillo);
                        bloque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "El agua está por debajo del 50%", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        tanque2.setText(object.getString("NIVEL_AGUA2")+"%");
                        bloque2.setBackgroundResource(R.color.greensh);
                        bloque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this, "EL nivel de agua en el tanque 2 es correcto", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    humedad.setText(object.getString("NIVEL_HUMEDAD")+"%");

                    ph.setText(object.getString("NIVEL_PH")+"%");

                    if((object.getInt("NIVEL_AGUA")<50&&object.getInt("NIVEL_AGUA2")<50)||object.getInt("NIVEL_AGUA")<=10||object.getInt("NIVEL_AGUA2")<=10){
                        cont.setBackgroundResource(R.drawable.orangeborder);
                        imaget.setImageResource(R.drawable.sad);
                        statet.setText("Estado\nLa planta necesita atención");

                    }else{
                        cont.setBackgroundResource(R.drawable.border);
                        imaget.setImageResource(R.drawable.japy);

                        statet.setText("Estado\nLa planta está bien");
                    }


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