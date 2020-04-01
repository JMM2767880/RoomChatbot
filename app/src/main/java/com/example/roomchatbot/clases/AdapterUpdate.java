package com.example.roomchatbot.clases;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomchatbot.R;

import java.util.List;

public class AdapterUpdate extends RecyclerView.Adapter<AdapterUpdate.UpdateViewHolder> {
                    private List<Frase> items;
                    private EditText editText, editText1;

                    public static class UpdateViewHolder extends RecyclerView.ViewHolder {

        public TextView frase;
        private CardView cvUpdateFrase;

        public UpdateViewHolder(View v) {
            super(v);
            frase = v.findViewById(R.id.tvFraseRecycler);
            cvUpdateFrase = v.findViewById(R.id.cvUpdateFrase);
        }
    }

    public AdapterUpdate(List<Frase> items, EditText editText, EditText editText1) {
        this.items = items;
        this.editText = editText;
        this.editText1 = editText1;
    }

    public void update(List<Frase> lista)
    {
        items.clear();
        items.addAll(lista);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.update_card, viewGroup, false);
        return new UpdateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UpdateViewHolder viewHolder, int i) {
        viewHolder.frase.setText(items.get(i).getFrase());
        viewHolder.cvUpdateFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(viewHolder.frase.getText());
                editText1.setText(viewHolder.frase.getText());
            }
        });
    }
}