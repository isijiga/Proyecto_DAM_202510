package com.example.proyecto_dam_202510.Dash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Coleccion;
import com.example.proyecto_dam_202510.data.pojo.UsersColecciones;

import java.util.ArrayList;
import java.util.List;

public class MisColeccionesAdapter extends  RecyclerView.Adapter<MisColeccionesAdapter.MiViewHolder>{

    private List<Coleccion> listaColecciones = new ArrayList<>();


    @NonNull
    @Override
    public MisColeccionesAdapter.MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coleccion_layout, parent, false);
        return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisColeccionesAdapter.MiViewHolder holder, int position) {
    Coleccion itemActual = listaColecciones.get(position);
    holder.tv_coleccion_titulo.setText(itemActual.getNombre());
    holder.tv_coleccion_subtitulo.setText(itemActual.getImagenPortada());
    holder.tv_coleccion_estado.setText(itemActual.getCartasporSobre()+"");
    holder.iv_coleccion_imagen.setImageResource(0);



    }

    @Override
    public int getItemCount() {
        return listaColecciones.size();
    }

    public void setDatos(List<Coleccion> coleccions) {
        this.listaColecciones = coleccions;

    }

    public static class MiViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_coleccion_titulo;
        private TextView tv_coleccion_subtitulo;
        private TextView tv_coleccion_estado;
        private ImageView iv_coleccion_imagen;


        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_coleccion_titulo = itemView.findViewById(R.id.tv_coleccion_titulo);
            tv_coleccion_subtitulo = itemView.findViewById(R.id.tv_coleccion_subtitulo);
            tv_coleccion_estado = itemView.findViewById(R.id.tv_coleccion_estado);
            iv_coleccion_imagen = itemView.findViewById(R.id.iv_coleccion_imagen);
        }
        public void bind (Coleccion coleccion){
            tv_coleccion_titulo.setText(coleccion.getNombre());
            tv_coleccion_subtitulo.setText(coleccion.getId());
            tv_coleccion_estado.setText(String.valueOf(coleccion.getTotalCartas()));
            iv_coleccion_imagen.setImageResource(0);


        }
    }

}
