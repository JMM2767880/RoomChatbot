package com.example.roomchatbot.clases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "frase")
public class Frase {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "frase")
    private String frase;

    @ColumnInfo(name = "frase_traducida")
    private String frase_traducida;

    @Ignore
    public Frase(){

    }

    public Frase(long id, String frase, String frase_traducida) {
        this.id = id;
        this.frase = frase;
        this.frase_traducida = frase_traducida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getFrase_traducida() {
        return frase_traducida;
    }

    public void setFrase_traducida(String frase_traducida) {
        this.frase_traducida = frase_traducida;
    }

    @Override
    public String toString() {
        return "Frase{" +
                "id=" + id +
                ", frase='" + frase + '\'' +
                ", frase_traducida='" + frase_traducida + '\'' +
                '}';
    }
}
