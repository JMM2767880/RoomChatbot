package com.example.roomchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomchatbot.bd.MainViewModel;
import com.example.roomchatbot.clases.AdapterUpdate;
import com.example.roomchatbot.clases.Frase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UpdateFrase extends AppCompatActivity {

    private RecyclerView rvUpdate;
    private EditText etUpdate, etAuxiliarUpdate;
    private Button btUpdateFrase;

    private AdapterUpdate adapterUpdate;
    private RecyclerView.LayoutManager lManager;

    private MainViewModel mainViewModel;

    private List<Frase> getAllFrase;

    private Frase fraseId, fraseModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_frase);

        initComponents();
        init();
    }

    private void initComponents() {
        rvUpdate = findViewById(R.id.rvUpdate);
        etUpdate = findViewById(R.id.etUpdate);
        etAuxiliarUpdate = findViewById(R.id.etAuxiliarUpdate);
        btUpdateFrase = findViewById(R.id.btUpdateFrase);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void init() {

        try {
            getAllFrase = mainViewModel.getAllFrase();

            if (getAllFrase.size() == 0){
                System.out.println("si");
                btUpdateFrase.setEnabled(false);
                Toast.makeText(this, getResources().getString(R.string.noFrases), Toast.LENGTH_SHORT).show();
            }else{
                System.out.println("no");
                btUpdateFrase.setEnabled(true);
                rvUpdate.setHasFixedSize(true);

                lManager = new LinearLayoutManager(this);
                rvUpdate.setLayoutManager(lManager);

                adapterUpdate = new AdapterUpdate(getAllFrase, etUpdate, etAuxiliarUpdate);
                rvUpdate.setAdapter(adapterUpdate);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btUpdateFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUpdate.getText().toString().equals("")){
                    Toast.makeText(UpdateFrase.this, getResources().getString(R.string.noEmpty), Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        fraseId = mainViewModel.getFrase(etAuxiliarUpdate.getText().toString());

                        Hilo1 h1 = new Hilo1(etUpdate.getText().toString());

                        h1.start();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
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

                fraseModificar = new Frase(fraseId.getId(), etUpdate.getText().toString(), jsonObj99.getString("text"));

                mainViewModel.modifyFrase(fraseModificar);

                rvUpdate.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Frase> actualizacionLista = null;
                        try {
                            actualizacionLista = mainViewModel.getAllFrase();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        adapterUpdate.update(actualizacionLista);

                        Toast.makeText(UpdateFrase.this, getResources().getString(R.string.modifyFrase), Toast.LENGTH_SHORT).show();
                    }
                });

                etUpdate.post(new Runnable() {
                    @Override
                    public void run() {
                        etUpdate.setText("");
                    }
                });


            } catch (Throwable t) {
                System.out.println(t.toString());
            }
        }
    }
}
