package com.example.controltomate;

import static com.example.controltomate.MainActivity.CHANNEL_ID;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.app.Notification;
import androidx.core.app.NotificationCompat;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Background extends Service {
    private Handler handler;
    private Runnable runnable;
    private int counter = 0;
    @Override
    public void onCreate() {
        super.onCreate();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // Coloca la lógica que deseas ejecutar en segundo plano aquí
                Log.d("MyBackgroundService", "Contador: " + counter);

                counter++;
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(HTTPCommunication.SERVER_NAME + "getLastEstado.php", new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject object=(response.getJSONObject(0));
                            String nivel_php=object.getString("NIVEL_PH");
                            String nivel_humedad=object.getString("NIVEL_HUMEDAD");
                            int nivel_agua=object.getInt("NIVEL_AGUA");
                            if(nivel_agua<=14){
                                Notification notification = createNotification("El nivel de agua es bajo","Se recomienda abastecer agua en el tanque");
                                startForeground(counter, notification);
                            }

                            System.out.println(nivel_agua);


                        }catch (JSONException e){
                            Log.d("Background", e.getMessage());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);
                handler.postDelayed(this, 300000); // Repite cada segundo
            }
        };

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.post(runnable); // Inicia el runnable
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification createNotification(String title, String content) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent);

        return builder.build();
    }

}
