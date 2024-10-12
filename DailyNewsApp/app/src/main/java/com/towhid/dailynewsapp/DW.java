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

public class DW extends AppCompatActivity {

    private WebView dw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dw);

        dw=findViewById(R.id.dw);

        WebSettings webSettings=dw.getSettings();
        dw.setWebViewClient(new SameView());
        webSettings.setJavaScriptEnabled(true);
        dw.loadUrl("https://www.dw.com/en/top-stories/s-9097");

    }

    public  class SameView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}