package com.example.roomchatbot.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.roomchatbot.R;

import java.util.List;

public class AdapterChatbot extends RecyclerView.Adapter<AdapterChatbot.ChatbotViewHolder> {
    private List<Conversacion> items;

    public static class ChatbotViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView frase;
        public TextView enviada;

        public ChatbotViewHolder(View v) {
            super(v);
            frase = v.findViewById(R.id.tvConversacion);
            enviada = v.findViewById(R.id.tvRecibida);
        }
    }

    public AdapterChatbot(List<Conversacion> items) {
        this.items = items;
    }

    public void addConversacion(Conversacion conversacion) {
        items.add(conversacion);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ChatbotViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_card, viewGroup, false);
        return new ChatbotViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ChatbotViewHolder viewHolder, int i) {
        viewHolder.frase.setText(items.get(i).getFrase_enviada_es());
        viewHolder.enviada.setText(items.get(i).getFrase_recibida_es());
    }
}