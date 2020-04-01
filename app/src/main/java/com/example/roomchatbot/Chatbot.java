package com.example.roomchatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.roomchatbot.apibot.ChatterBot;
import com.example.roomchatbot.apibot.ChatterBotFactory;
import com.example.roomchatbot.apibot.ChatterBotSession;
import com.example.roomchatbot.apibot.ChatterBotType;
import com.example.roomchatbot.bd.MainViewModel;
import com.example.roomchatbot.clases.AdapterChatbot;
import com.example.roomchatbot.clases.Conversacion;
import com.example.roomchatbot.clases.Frase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Chatbot extends AppCompatActivity {

    private ChatterBot bot;
    private ChatterBotSession botSession;

    private RecyclerView rvConversacion;
    private RecyclerView.LayoutManager lManager;

    private AdapterChatbot adapterChatbot;

    private List<Conversacion> conversaciones;
    private List<Frase> todasFrases;

    private MainViewModel mainViewModel;

    private Button btFraseAleatoria;

    private Conversacion conversacionRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        initComponents();
        init();
    }

    private void initComponents() {
        rvConversacion = findViewById(R.id.rvChatBot);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        conversaciones = new ArrayList<>();
        btFraseAleatoria = findViewById(R.id.btFraseAleatoria);
    }

    private void init() {
        try {
            conversaciones = mainViewModel.getAllConversaciones();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        rvConversacion.setHasFixedSize(true);

        lManager = new LinearLayoutManager(this);
        rvConversacion.setLayoutManager(lManager);

        adapterChatbot = new AdapterChatbot(conversaciones);
        rvConversacion.setAdapter(adapterChatbot);

        btFraseAleatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btFraseAleatoria.setEnabled(false);

                    todasFrases = mainViewModel.getAllFrase();

                    if (todasFrases == null){
                        Toast.makeText(Chatbot.this, getResources().getString(R.string.noFrases), Toast.LENGTH_SHORT).show();
                    }else{
                        int valorDado = (int) Math.floor(Math.random()*todasFrases.size());
                        System.out.println(valorDado + ": " + todasFrases.get(valorDado).getFrase());

                        Chatbot1 chatbot1 = new Chatbot1(todasFrases.get(valorDado).getFrase());

                        chatbot1.start();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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

    public String descomponerJSON(String respuesta) throws JSONException {
        JSONArray jsonArr = new JSONArray(respuesta);

        JSONObject jsonObj = jsonArr.getJSONObject(0);

        System.out.println(jsonObj);

        JSONArray jsonArr1 = jsonObj.getJSONArray("translations");

        JSONObject jsonObj99 = jsonArr1.getJSONObject(0);

        String palabra = jsonObj99.getString("text");

        return palabra;
    }

    public class Chatbot1 extends Thread{
        private String text;

        public Chatbot1(String text) {
            this.text = text;
        }

        @Override
        public void run() {
            super.run();

            try {
                Calendar calendario = Calendar.getInstance();

                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);

                String dia = Integer.toString(calendario.get(Calendar.DATE));
                String mes = Integer.toString(calendario.get(Calendar.MONTH));
                String anio = Integer.toString(calendario.get(Calendar.YEAR));

                conversacionRoom = new Conversacion();
                conversacionRoom.setId(0);

                conversacionRoom.setFrase_enviada_es(text);

                conversacionRoom.setHora(hora + ":" + minutos);

                conversacionRoom.setFecha(dia + "/" + mes + "/" + anio);

                String body = "fromLang=es&text=" + text + "&to=en";
                String src = "https://www.bing.com/ttranslatev3";

                conversacionRoom.setFrase_enviada_en(descomponerJSON(postHttp(src, body)));

                ChatterBotFactory factory = new ChatterBotFactory();

                ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");

                ChatterBotSession bot2session = bot2.createSession();


                String bot2En = bot2session.think(conversacionRoom.getFrase_enviada_en());

                System.out.println(bot2En);

                conversacionRoom.setFrase_recibida_en(bot2En);

                System.out.println(conversacionRoom.toString());

                String body1 = "fromLang=en&text=" + conversacionRoom.getFrase_recibida_en() + "&to=es";

                System.out.println(postHttp(src, body1));

                conversacionRoom.setFrase_recibida_es(descomponerJSON(postHttp(src, body1)));

                rvConversacion.post(new Runnable() {
                    @Override
                    public void run() {
                        adapterChatbot.addConversacion(conversacionRoom);
                    }
                });

                btFraseAleatoria.post(new Runnable() {
                    @Override
                    public void run() {
                        btFraseAleatoria.setEnabled(true);
                    }
                });

                System.out.println(conversacionRoom.toString());

                mainViewModel.insertConversacion(conversacionRoom);

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("no entró");
            }
        }
    }
}
