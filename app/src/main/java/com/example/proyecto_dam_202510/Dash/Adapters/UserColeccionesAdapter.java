package com.example.proyecto_dam_202510.Dash.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;
import com.example.proyecto_dam_202510.databinding.UserscoleccionLayoutBinding;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Clase que facilita los datos necesario al RecyclerView de las colecciones que está siguiendo cada
 * usuario.
 */
public class UserColeccionesAdapter extends RecyclerView.Adapter<UserColeccionesAdapter.MiViewHolder> {

    private List<UsersColecciones> listaColecciones = new ArrayList<>();
    private onItemClickListener listener;

    public interface onItemClickListener {
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
        try {
            holder.bind(itemActual);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

        public void bind(UsersColecciones item) throws ParseException {

            binding.tvColeccionTitulo.setText(item.getNombreColeccion());
            binding.tvColeccionSubtitulo.setText("Total cartas:" + item.getProgreso() + "/" + item.getTotalCromos());

            /*paso a formato local dd/MM/aaaa hh:mm*/

            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String fechaFormateada = formatoSalida.format(item.getInicioColeccion());
            String hoyFormateada = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            Date fechaInicio = formatoSalida.parse(fechaFormateada);
            Date fechaHoy = formatoSalida.parse(hoyFormateada);
            long diffMillis = fechaHoy.getTime() - fechaInicio.getTime();
            long diffDias = diffMillis / (24 * 60 * 60 * 1000);
            binding.tvColeccionEstado.setText(fechaFormateada + "(Hace: " + diffDias + " dias)");
            Picasso.get().load(item.getImagen())
                    .fit()
                    .into(binding.ivColeccionImagen);
            binding.tvColeccionProgreso.setProgress(item.getProgreso());
        }


    }
}