package com.example.roomchatbot.clases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "conversacion")
public class Conversacion {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "hora")
    private String hora;

    @ColumnInfo(name = "fecha")
    private String fecha;

    @ColumnInfo(name = "frase_enviada_es")
    private String frase_enviada_es;

    @ColumnInfo(name = "frase_enviada_en")
    private String frase_enviada_en;

    @ColumnInfo(name = "frase_recibida_es")
    private String frase_recibida_es;

    @ColumnInfo(name = "frase_recibida_en")
    private String frase_recibida_en;

    @Ignore
    public Conversacion() {

    }

    public Conversacion(long id, String hora, String fecha, String frase_enviada_es, String frase_enviada_en, String frase_recibida_es, String frase_recibida_en) {
        this.id = id;
        this.hora = hora;
        this.fecha = fecha;
        this.frase_enviada_es = frase_enviada_es;
        this.frase_enviada_en = frase_enviada_en;
        this.frase_recibida_es = frase_recibida_es;
        this.frase_recibida_en = frase_recibida_en;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFrase_enviada_es() {
        return frase_enviada_es;
    }

    public void setFrase_enviada_es(String frase_enviada_es) {
        this.frase_enviada_es = frase_enviada_es;
    }

    public String getFrase_enviada_en() {
        return frase_enviada_en;
    }

    public void setFrase_enviada_en(String frase_enviada_en) {
        this.frase_enviada_en = frase_enviada_en;
    }

    public String getFrase_recibida_es() {
        return frase_recibida_es;
    }

    public void setFrase_recibida_es(String frase_recibida_es) {
        this.frase_recibida_es = frase_recibida_es;
    }

    public String getFrase_recibida_en() {
        return frase_recibida_en;
    }

    public void setFrase_recibida_en(String frase_recibida_en) {
        this.frase_recibida_en = frase_recibida_en;
    }

    @Override
    public String toString() {
        return "Conversación{" +
                "id=" + id +
                ", hora='" + hora + '\'' +
                ", fecha='" + fecha + '\'' +
                ", frase_enviada_es='" + frase_enviada_es + '\'' +
                ", frase_enviada_en='" + frase_enviada_en + '\'' +
                ", frase_recibida_es='" + frase_recibida_es + '\'' +
                ", frase_recibida_en='" + frase_recibida_en + '\'' +
                '}';
    }
}
