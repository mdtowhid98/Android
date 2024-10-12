package com.towhid.dailynewsapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DhakaTribune extends AppCompatActivity {

    private WebView dhakaTribune;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dhaka_tribune);

        dhakaTribune=findViewById(R.id.btnDhakaTribune);

        WebSettings webSettings=dhakaTribune.getSettings();
        dhakaTribune.setWebViewClient(new SameView());
        webSettings.setJavaScriptEnabled(true);
        dhakaTribune.loadUrl("https://www.dhakatribune.com/");

    }

    public  class SameView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}