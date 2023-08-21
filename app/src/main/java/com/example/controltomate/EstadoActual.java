package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EstadoActual extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_actual);
        EditText editTextPH = findViewById(R.id.editTextPH);
        EditText editTextHumedad = findViewById(R.id.editTextHumedad);
        EditText editTextAgua = findViewById(R.id.editTextAgua);
        EditText editTextAgua2 = findViewById(R.id.editTextAgua2);

        Button ver=findViewById(R.id.btnVer);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ver=new Intent(EstadoActual.this, VerPlanta.class);
                startActivity(ver);
            }
        });




        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(HTTPCommunication.SERVER_NAME + "getLastEstado.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
    JSONObject object=(response.getJSONObject(0));
                    editTextPH.setText(object.getString("NIVEL_PH"));
                    editTextHumedad.setText(object.getString("NIVEL_HUMEDAD")+"%");
                    editTextAgua.setText(object.getString("NIVEL_AGUA")+"%");
                    editTextAgua2.setText(object.getString("NIVEL_AGUA2")+"%");




                    String longblobBase64 = object.getString("FOTO");


                    // Decodifica el valor base64 a un array de bytes
                   byte[] imageBytes = Base64.decode(longblobBase64, Base64.DEFAULT);

                    // Decodifica el byte[] en un Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


                }catch (JSONException e){
                    System.out.println(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }
}