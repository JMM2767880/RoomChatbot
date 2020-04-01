package com.example.roomchatbot.bd;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.roomchatbot.clases.Conversacion;
import com.example.roomchatbot.clases.Frase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public void insertFrase(Frase frase) {
        repository.insertFrase(frase);
    }

    public void modifyFrase(Frase frase) {
        repository.modifyFrase(frase);
    }

    public Frase getFrase(String frase) throws ExecutionException, InterruptedException {
        return repository.getFrase(frase);
    }

    public List<Frase> getAllFrase() throws ExecutionException, InterruptedException {
        return repository.getAllFrase();
    }

    /*CONVERSACION*/
    public List<Conversacion> getAllConversaciones() throws ExecutionException, InterruptedException {
        return repository.getAllConversaciones();
    }

    public void insertConversacion(Conversacion conversacion) {
        repository.insertConversacion(conversacion);
    }
}
