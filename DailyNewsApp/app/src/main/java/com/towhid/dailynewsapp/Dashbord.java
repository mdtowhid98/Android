package com.towhid.dailynewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dashbord extends AppCompatActivity {

    private Button btnBdnews;
    private Button btnDhakaTribune;
    private Button btndw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashbord);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       btnBdnews=findViewById(R.id.btnBdnews);

       btnBdnews.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(), BDNews24.class);
               startActivity(intent);
           }
       });

       btnDhakaTribune=findViewById(R.id.btnDhakaTribune);

       btnDhakaTribune.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(), DhakaTribune.class);
               startActivity(intent);
           }
       });

        btndw=findViewById(R.id.btndw);

        btndw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), DW.class);
                startActivity(intent);
            }
        });

    }
}