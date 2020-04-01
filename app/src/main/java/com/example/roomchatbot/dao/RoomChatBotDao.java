package com.example.roomchatbot.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomchatbot.clases.Conversacion;
import com.example.roomchatbot.clases.Frase;

import java.util.List;

@Dao
public interface RoomChatBotDao {

    /*FRASE*/
    @Insert
    long insertFrase(Frase frase);

    @Update
    int updateFrase(Frase... frases);

    @Delete
    int deleteFrase(Frase... frases);

    @Query("SELECT * FROM frase")
    List<Frase> AllFrases();

    @Query("SELECT * FROM frase WHERE frase=:frase")
    Frase frase(String frase);

    /*CONVERSACION*/
    @Insert
    long insertConversacion(Conversacion conversacion);

    @Update
    int updateConversacion(Conversacion... conversaciones);

    @Delete
    int deleteFrae(Conversacion... conversaciones);

    @Query("SELECT * FROM conversacion")
    List<Conversacion> AllConversaciones();

}
