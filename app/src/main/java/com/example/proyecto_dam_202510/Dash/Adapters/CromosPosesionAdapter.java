package com.example.proyecto_dam_202510.Dash.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.data.pojo.CromoPosesion;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupado;
import com.example.proyecto_dam_202510.databinding.CromoposesionLayoutBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;


/**
 * la clase CromosAdapter enlaza cromos
 * que un usario posee con la vista de datos el RecicledView del Fragment.
 */
public class CromosPosesionAdapter extends RecyclerView.Adapter<CromosPosesionAdapter.MiViewHolder> {

    private List<CromoPosesion> listaCromosPosesion = new ArrayList<>();
    private List<CromoPosesionAgrupado> listaCromosPosesionAgrupado = new ArrayList<>();
    private onItemClickListener listener;
    private borrarListener borrarListener;

    public interface onItemClickListener {
        void onItemClick(CromoPosesionAgrupado CromoPosesionAgrupado);
    }

    public interface borrarListener {
        void borrar(CromoPosesionAgrupado CromoPosesionAgrupado);
    }

    public void setBorrarListener(borrarListener listener) {
        this.borrarListener = listener;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public CromosPosesionAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CromoposesionLayoutBinding binding = CromoposesionLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CromosPosesionAdapter.MiViewHolder holder, int position) {
        CromoPosesionAgrupado itemActual = listaCromosPosesionAgrupado.get(position);
        holder.bind(itemActual);
    }

    @Override
    public int getItemCount() {
        return listaCromosPosesionAgrupado.size();
    }

    public void setDatos(List<CromoPosesionAgrupado> cromos) {
        this.listaCromosPosesionAgrupado = cromos;
        notifyDataSetChanged();
    }

    public class MiViewHolder extends RecyclerView.ViewHolder {

        private com.example.proyecto_dam_202510.databinding.CromoposesionLayoutBinding binding;

        public MiViewHolder(@NonNull CromoposesionLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            /**
             * Escuchador para borrar un item de la lista. Cuando el usuario deja un cromo pulsado
             * se lanza el metodo borrar del listener.
             */
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    borrarListener.borrar(listaCromosPosesionAgrupado.get(getBindingAdapterPosition()));
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(listaCromosPosesionAgrupado.get(getBindingAdapterPosition()));
                }
            });

        }

        public void bind(CromoPosesionAgrupado item) {
            binding.tvCromoTitulo.setText(item.getNombre());
            binding.tvCartaSubtitulo.setText(String.valueOf(item.getNumero()));
            binding.tvCartaEstado.setText(String.valueOf(item.getFechaAdquisicion()));
            binding.tvCantidadRepetidas.setText(String.valueOf(item.getRepetida()));
            Picasso.get().load(item.getImagen()).into(binding.ivCromoImagen);
        }
    }
}