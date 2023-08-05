package com.example.controltomate;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HTTPCommunication {
    public static final String SERVER_NAME="https://controltomate.000webhostapp.com/data/";

    public static void PostRequest(String keys[], String values[], String url, Context context, String message){
        StringRequest request=  new StringRequest(Request.Method.POST, SERVER_NAME + url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
}
