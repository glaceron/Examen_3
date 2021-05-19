package com.example.examen3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaHolder>
{
    private List<Mascota> mascotas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MascotaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mascota_item, parent, false);
        return new MascotaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaHolder holder, int position)
    {

        Mascota currentMascota = mascotas.get(position);
        holder.textViewNombre.setText(currentMascota.getNombre());
        holder.textViewRaza.setText(currentMascota.getRaza());
        holder.textViewEdad.setText(currentMascota.getEdad());
        Picasso.get().load(currentMascota.getImagen()).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(holder.imageViewImagen);

    }

    @Override
    public int getItemCount()
    {
        return mascotas.size();
    }

    public void setMascotas(List<Mascota> mascotas)
    {
        this.mascotas = mascotas;

        notifyDataSetChanged();
    }

    public Mascota getMascotaAt(int position)
    {
        return mascotas.get(position);
    }

    class MascotaHolder extends RecyclerView.ViewHolder
    {
        private TextView textViewNombre;
        private TextView textViewRaza;
        private TextView textViewEdad;
        private ImageView imageViewImagen;

        public MascotaHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewRaza = itemView.findViewById(R.id.textViewRaza);
            textViewEdad = itemView.findViewById(R.id.textViewEdad);
            imageViewImagen = itemView.findViewById(R.id.imageViewImagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION)
                    {
                    listener.onIntemClick(mascotas.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener
    {
        void onIntemClick(Mascota mascota);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

}
