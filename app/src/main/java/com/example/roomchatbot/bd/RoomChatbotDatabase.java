package com.example.roomchatbot.bd;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomchatbot.clases.Conversacion;
import com.example.roomchatbot.clases.Frase;
import com.example.roomchatbot.dao.RoomChatBotDao;

@Database(entities = {Frase.class, Conversacion.class}, version = 1, exportSchema = false)
public abstract class RoomChatbotDatabase extends RoomDatabase {
    public abstract RoomChatBotDao getRoomChatbotDAO();

    private static volatile RoomChatbotDatabase INSTANCIA;

    public static RoomChatbotDatabase getDatabase(final Context context) {
        if (INSTANCIA == null) {
            synchronized (RoomChatbotDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                            RoomChatbotDatabase.class, "roomchatbot.sqlite")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}
