package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Historial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(HTTPCommunication.SERVER_NAME + "consultarEstados.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject object=response.getJSONObject(i);
                        addDataRow(tableLayout,object.getString("NIVEL_PH"),object.getString("NIVEL_HUMEDAD"),object.getString("NIVEL_AGUA"),object.getString("STATUS"),object.getString("HORA"),object.getString("FECHA"));
                    }


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

    private void addDataRow(TableLayout tableLayout, String ph, String humedad, String agua, String estado, String hora, String fecha) {
        TableRow tableRow = new TableRow(this);

        TextView textViewPH = createTextView(ph);
        TextView textViewHumedad = createTextView(humedad);
        TextView textViewAgua = createTextView(agua);
        TextView textViewEstado = createTextView(estado);
        TextView textViewHora = createTextView(hora);
        TextView textViewFecha = createTextView(fecha);

        tableRow.addView(textViewPH);
        tableRow.addView(textViewHumedad);
        tableRow.addView(textViewAgua);
        tableRow.addView(textViewEstado);
        tableRow.addView(textViewHora);
        tableRow.addView(textViewFecha);

        tableLayout.addView(tableRow);
    }
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        textView.setText(text);
        return textView;
    }
}