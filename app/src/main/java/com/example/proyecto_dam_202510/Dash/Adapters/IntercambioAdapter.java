package com.example.proyecto_dam_202510.Dash.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.R; // Importa tu R
import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para confeccionar la lista de cromo para intercambiar. Recibe una lista de cromos y se lo pasa al Fragment.
 */
public class IntercambioAdapter extends RecyclerView.Adapter<IntercambioAdapter.CromoViewHolder> {
    private List<CromoPosesionAgrupadoIntercambio> listaCromos = new ArrayList<>();
    private OnItemClickListener listener;
    public static class CromoViewHolder extends RecyclerView.ViewHolder {
        TextView tv_numero;
        TextView tvNombre;
        TextView tvConteo;
        TextView tvOwners;
        TextView tvColeccion;

        public CromoViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_numero = itemView.findViewById(R.id.tv_numero);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvConteo = itemView.findViewById(R.id.tv_conteo);
            tvOwners = itemView.findViewById(R.id.tv_owners);
            tvColeccion = itemView.findViewById(R.id.tv_coleccion);

        }
    }


    @NonNull
    @Override
    public CromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cromo_simple, parent, false);
        return new CromoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CromoViewHolder holder, int position) {

        CromoPosesionAgrupadoIntercambio cromo = listaCromos.get(position);
        holder.tv_numero.setText(cromo.getNumero() + " - ");
        holder.tvNombre.setText(cromo.getNombre());
        holder.tvColeccion.setText("Colección: " + cromo.getColeccionId());
        holder.tvConteo.setText("Repetidos: " + cromo.getRepetida());


        if (cromo.getUsuarioPoseedor() != null) {
            holder.tvOwners.setText("Dueños: " + cromo.getUsuarioPoseedor().toString());
        } else {
            holder.tvOwners.setText("Dueños: (vacío)");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(cromo);

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listaCromos.size();
    }

    public void setCromos(List<CromoPosesionAgrupadoIntercambio> nuevaLista) {
        this.listaCromos = nuevaLista;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener{
        void onItemClick(CromoPosesionAgrupadoIntercambio cromo);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}