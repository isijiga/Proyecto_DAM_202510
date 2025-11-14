package com.example.proyecto_dam_202510.Dash.Adapters;

import android.app.appsearch.PackageIdentifier;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.data.pojo.Cromo;
import com.example.proyecto_dam_202510.databinding.CromoLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CromosAdapter extends RecyclerView.Adapter<CromosAdapter.MiViewHolder> {

    private List<Cromo> listaCromos = new ArrayList<>();
    private onItemClickListener listener;
    public interface onItemClickListener{
        void onItemClick(Cromo cromo);
    }
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public CromosAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CromoLayoutBinding binding = CromoLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CromosAdapter.MiViewHolder holder, int position) {
        Cromo itemActual = listaCromos.get(position);
        holder.bind(itemActual);
            }

    @Override
    public int getItemCount() {
        return listaCromos.size();
    }

    public void setDatos(List<Cromo> cromos) {
        this.listaCromos = cromos;
        notifyDataSetChanged();
    }

        public class MiViewHolder extends RecyclerView.ViewHolder {

        private CromoLayoutBinding binding;

        public MiViewHolder(@NonNull CromoLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(listaCromos.get(getBindingAdapterPosition()));
                }
            });

        }

        public void bind(Cromo item) {

            binding.tvCromoTitulo.setText(item.getNombre());
            binding.tvCartaSubtitulo.setText(String.valueOf(item.getTipo()));
            binding.tvCartaEstado.setText(String.valueOf(item.getId()));
            Picasso.get().load(item.getImagen()).fit().into(binding.ivCromoImagen);

        }


    }
}