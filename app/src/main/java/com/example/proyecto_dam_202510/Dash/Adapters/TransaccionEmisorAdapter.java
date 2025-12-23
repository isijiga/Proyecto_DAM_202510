package com.example.proyecto_dam_202510.Dash.Adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.CromoPosesionAgrupadoIntercambio;
import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.example.proyecto_dam_202510.databinding.TransaccionEmisorLayoutBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Clase Adapter para la lista de transacciones de peticiones enviadas.
 */
public class TransaccionEmisorAdapter extends RecyclerView.Adapter<TransaccionEmisorAdapter.TransaccionEmisorViewHolder>  {

    private List<Transaccion> listaTransacciones = new ArrayList<>();

    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;

    public TransaccionEmisorAdapter() {

    }

    public interface OnItemLongClickListener{
        void onItemLongClick(Transaccion transaccion);
    }


    public interface OnItemClickListener{
        void onItemClick(Transaccion transaccion);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }



    @NonNull
    @Override
    public TransaccionEmisorAdapter.TransaccionEmisorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaccion_emisor_layout, parent, false);
        return new TransaccionEmisorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaccionEmisorAdapter.TransaccionEmisorViewHolder holder, int position) {
        Transaccion transaccion = listaTransacciones.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String fechaAdquision = sdf.format(transaccion.getFechaAdquisicion());


        holder.binding.tvItemNumero.setText(transaccion.getNumero());
        holder.binding.tvItemNombre.setText(transaccion.getNombre());
        holder.binding.tvItemEstado.setText(transaccion.getEstado());
        holder.binding.tvItemFecha.setText(fechaAdquision);
        holder.binding.tvItemColeccion.setText(transaccion.getColeccionId());
        holder.binding.tvItemPedidoPor.setText(transaccion.getEmailPedidoA());

        switch (transaccion.getEstado()){
            case "pendiente":
                holder.binding.EstadoIco.setImageResource(R.drawable.pending);
                break;
            case "ACEPTADA. Pdte Envio":
                holder.binding.EstadoIco.setImageResource(R.drawable.accept);
                break;
            case "ENVIADA":
                holder.binding.EstadoIco.setImageResource(R.drawable.send);
                break;
        }
        if(transaccion.getMensajeRespuesta()!=null && !transaccion.getMensaje().isEmpty() ){
            holder.binding.message.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.message.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(transaccion);


                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickListener.onItemLongClick(transaccion);
                return true;
            }
        });






    }

    @Override
    public int getItemCount() {
        return listaTransacciones.size();
    }

    public void setListaTransacciones(List<Transaccion> transaccion) {
        this.listaTransacciones = transaccion;
        notifyDataSetChanged();
    }

    public class TransaccionEmisorViewHolder extends RecyclerView.ViewHolder {
        private TransaccionEmisorLayoutBinding binding;
        public TransaccionEmisorViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransaccionEmisorLayoutBinding.bind(itemView);

        }
    }
}

