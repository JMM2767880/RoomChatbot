package com.example.roomchatbot.bd;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.roomchatbot.clases.Conversacion;
import com.example.roomchatbot.clases.Frase;
import com.example.roomchatbot.dao.RoomChatBotDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Repository {

    private RoomChatBotDao roomChatBotDao;

    public Repository(Context contexto) {
        RoomChatbotDatabase db = RoomChatbotDatabase.getDatabase(contexto);
        roomChatBotDao = db.getRoomChatbotDAO();
    }

    /*FRASE*/
    public void insertFrase(Frase frase) {
        new InsertThreadFrase().execute(frase);
    }

    private class InsertThreadFrase extends AsyncTask<Frase, Void, Void> {

        @Override
        protected Void doInBackground(Frase... frases) {
            roomChatBotDao.insertFrase(frases[0]);
            Log.v("xyz", frases[0].toString());
            return null;
        }
    }

    public void modifyFrase(Frase frase) {
        new ModifyThreadFrase().execute(frase);
    }

    private class ModifyThreadFrase extends AsyncTask<Frase, Void, Void> {

        @Override
        protected Void doInBackground(Frase... frases) {
            roomChatBotDao.updateFrase(frases[0]);
            return null;
        }
    }

    public Frase getFrase(String frase) throws ExecutionException, InterruptedException {
        return new ThreadFrase().execute(frase).get();
    }

    private class ThreadFrase extends AsyncTask<String, Void, Frase> {

        @Override
        protected Frase doInBackground(String... strings) {

            return roomChatBotDao.frase(strings[0]);
        }
    }

    public List<Frase> getAllFrase() throws ExecutionException, InterruptedException {
        return new ThreadAllFrase().execute().get();
    }

    private class ThreadAllFrase extends AsyncTask<Void, Void, List<Frase>> {


        @Override
        protected List<Frase> doInBackground(Void... voids) {
            return roomChatBotDao.AllFrases();
        }
    }

    /*CONVERSACIÓN*/

    public List<Conversacion> getAllConversaciones() throws ExecutionException, InterruptedException {
        return new ThreadAllConversaciones().execute().get();
    }

    private class ThreadAllConversaciones extends AsyncTask<Void, Void, List<Conversacion>> {

        @Override
        protected List<Conversacion> doInBackground(Void... voids) {
            return roomChatBotDao.AllConversaciones();
        }
    }

    public void insertConversacion(Conversacion conversacion) {
        new InsertThreadConversacion().execute(conversacion);
    }

    private class InsertThreadConversacion extends AsyncTask<Conversacion, Void, Void> {

        @Override
        protected Void doInBackground(Conversacion... conversacions) {
            roomChatBotDao.insertConversacion(conversacions[0]);
            Log.v("xyz", conversacions[0].toString());
            return null;
        }
    }
}
