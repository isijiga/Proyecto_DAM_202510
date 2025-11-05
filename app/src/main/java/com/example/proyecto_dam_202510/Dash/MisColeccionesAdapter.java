package com.example.proyecto_dam_202510.Dash;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.databinding.ColeccionLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MisColeccionesAdapter extends  RecyclerView.Adapter<MisColeccionesAdapter.MiViewHolder>{

    private List<Coleccion> listaColecciones = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Coleccion coleccion);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public MisColeccionesAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ColeccionLayoutBinding binding = ColeccionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MisColeccionesAdapter.MiViewHolder holder, int position) {
    Coleccion itemActual = listaColecciones.get(position);
    holder.bind(itemActual);



    }

    @Override
    public int getItemCount() {
        return listaColecciones.size();
    }

    public void setDatos(List<Coleccion> coleccions) {
        this.listaColecciones = coleccions;
        notifyDataSetChanged();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder{

        private ColeccionLayoutBinding binding;

        public MiViewHolder(@NonNull ColeccionLayoutBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(listaColecciones.get(getBindingAdapterPosition()));
                }
            });
        }
        public void bind (Coleccion coleccion){
            binding.tvColeccionTitulo.setText(coleccion.getNombre());
            binding.tvColeccionSubtitulo.setText(coleccion.getId());
            binding.tvColeccionEstado.setText(String.valueOf(coleccion.getTotalCartas()));
            binding.ivColeccionImagen.setImageResource(0);


        }


    }

}
