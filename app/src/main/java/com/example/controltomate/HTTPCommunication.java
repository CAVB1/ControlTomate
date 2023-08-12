package com.example.controltomate;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HTTPCommunication {
    public static final String SERVER_NAME="https://controltomate.000webhostapp.com/data/";

    public static void PostRequest(String keys[], String values[], String url, Context context, String message,String errorMessage){
        StringRequest request=  new StringRequest(Request.Method.POST, SERVER_NAME + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,errorMessage,Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<String,String>();
                for(int i=0; i< keys.length;i++){
                    params.put(keys[i],values[i]);
                }

                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    public static void GetRequest(String keys[], String values[], String url, Context context, String message){
        url=url+"?";
        for (int i=0;i< keys.length;i++){
            url+=keys[i]+"="+values[i]+"&";
        }
        url=url.substring(0,url.length()-1);

        JSONArray res=new JSONArray();
        JsonArrayRequest stringRequest= new JsonArrayRequest(SERVER_NAME + url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                for (int i=0; i<response.length();i++){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        res.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }
}
