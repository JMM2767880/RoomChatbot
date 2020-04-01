package com.example.roomchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomchatbot.bd.MainViewModel;
import com.example.roomchatbot.clases.Frase;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class AddFrase extends AppCompatActivity {

    private Button btAddF;
    private EditText etFrase;

    private Frase fraseAdd;

    private String frase;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_frase);

        initComponents();
        init();
    }

    private void initComponents() {
        btAddF = findViewById(R.id.btAddF);
        etFrase = findViewById(R.id.etFrase);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void init() {
        btAddF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frase = etFrase.getText().toString();

                try {
                    Frase f = mainViewModel.getFrase(frase);

                    if (f == null){
                        Hilo1 h = new Hilo1(frase);
                        h.start();
                    }else{
                        Toast.makeText(AddFrase.this, getResources().getString(R.string.fraseNoAdd), Toast.LENGTH_SHORT).show();
                    }
                    
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

            //https://www.bing.com/ttranslatev3?isVertical=1&&IG=FD1263D89EE54C23BB6D84E18E5DD979&IID=translator.5026.1
    }

    public String postHttp(String src, String body) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(body);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line + "\n");
            }
            in.close();
        } catch (IOException e) {
        }
        return buffer.toString();
    }

    public class Hilo1 extends Thread{
        private String texto;

        public Hilo1(String texto) {
            this.texto = texto;
        }

        @Override
        public void run() {
            String body = "fromLang=es&text=" + texto + "&to=en";
            String src = "https://www.bing.com/ttranslatev3";
            String respuesta = postHttp(src, body);

            System.out.println(respuesta);

            try {

                JSONArray jsonArr = new JSONArray(respuesta);

                JSONObject jsonObj = jsonArr.getJSONObject(0);

                System.out.println(jsonObj);

                JSONArray jsonArr1 = jsonObj.getJSONArray("translations");

                JSONObject jsonObj99 = jsonArr1.getJSONObject(0);

                System.out.println(jsonObj99.getString("text"));

                fraseAdd = new Frase(0, texto, jsonObj99.getString("text"));

                mainViewModel.insertFrase(fraseAdd);

                etFrase.post(new Runnable() {
                    public void run() {
                        etFrase.setText("");
                        Toast.makeText(AddFrase.this, getResources().getString(R.string.fraseAdd), Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Throwable t) {
                System.out.println(t.toString());
            }
        }
    }
}
