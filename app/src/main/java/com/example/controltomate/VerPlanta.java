package com.example.controltomate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VerPlanta extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_planta);
        webView = findViewById(R.id.webView);

        // Habilitar JavaScript (si es necesario)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Cargar la URL deseada

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Una vez que la página se ha cargado, ejecuta JavaScript para mostrar solo la imagen deseada
                String jsCode = "var img=document.body.getElementsById('stream')" +
                        "var elements = document.body.getElementsByTagName('div');" +
                        "for (var i = 0; i < elements.length; i++) {" +
                        "   if (elements[i].id === 'stream') {" +
                        "       elements[i].style.display = 'block';" +
                        "   } else {" +
                        "       elements[i].style.display = 'none';" +
                        "   }" +
                        "}";

                webView.evaluateJavascript(jsCode, null);
            }
        });

        webView.loadUrl("http://192.168.137.25");
    }
}