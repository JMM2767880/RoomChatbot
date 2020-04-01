package com.example.roomchatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addFrase, updateFrase, btChatbot;
    private Intent intentAddFrase, intentUpdateFrase, intentChatbot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        init();
    }

    private void initComponents() {
        addFrase = findViewById(R.id.btAddFrase);
        updateFrase = findViewById(R.id.btUpdateFrase);
        btChatbot = findViewById(R.id.btChatbot);
    }

    private void init() {
        addFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentAddFrase = new Intent(MainActivity.this, AddFrase.class);
                startActivity(intentAddFrase);
            }
        });

        updateFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentUpdateFrase = new Intent(MainActivity.this, UpdateFrase.class);
                startActivity(intentUpdateFrase);
            }
        });

        btChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentChatbot = new Intent(MainActivity.this, Chatbot.class);
                startActivity(intentChatbot);
            }
        });
    }
}
