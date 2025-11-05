package com.example.proyecto_dam_202510.Dash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.example.proyecto_dam_202510.databinding.UserscoleccionLayoutBinding;
import java.util.ArrayList;
import java.util.List;

public class UserColeccionesAdapter extends RecyclerView.Adapter<UserColeccionesAdapter.MiViewHolder> {

    private List<UsersColecciones> listaColecciones = new ArrayList<>();
    private onItemClickListener listener;
    public interface onItemClickListener{
        void onItemClick(UsersColecciones userColeccion);
    }
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public UserColeccionesAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        UserscoleccionLayoutBinding binding = UserscoleccionLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MiViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserColeccionesAdapter.MiViewHolder holder, int position) {
        UsersColecciones itemActual = listaColecciones.get(position);
        holder.bind(itemActual);
            }

    @Override
    public int getItemCount() {
        return listaColecciones.size();
    }

    public void setDatos(List<UsersColecciones> coleccions) {
        this.listaColecciones = coleccions;

        notifyDataSetChanged();
    }

        public class MiViewHolder extends RecyclerView.ViewHolder {

        private UserscoleccionLayoutBinding binding;

        public MiViewHolder(@NonNull UserscoleccionLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(listaColecciones.get(getBindingAdapterPosition()));
                }
            });

        }

        public void bind(UsersColecciones item) {

            binding.tvColeccionTitulo.setText(item.getNombreColeccion());
            binding.tvColeccionSubtitulo.setText(String.valueOf(item.getProgreso()));
            binding.tvColeccionEstado.setText(String.valueOf(item.getInicioColeccion()));

            binding.ivColeccionImagen.setImageResource(0);
        }


    }
}