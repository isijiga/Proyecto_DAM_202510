package com.example.proyecto_dam_202510.Dash.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.data.pojo.Transaccion;
import com.example.proyecto_dam_202510.databinding.TransaccionLayoutBinding;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransaccionAdapter extends RecyclerView.Adapter<TransaccionAdapter.TransaccionViewHolder> {

    private List<Transaccion> listaTransacciones = new ArrayList<>();

    public TransaccionAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
       void onItemClick(Transaccion transaccion);
    }
    private final OnItemClickListener listener;


    @NonNull
    @Override
    public TransaccionAdapter.TransaccionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaccion_layout, parent, false);
        return new TransaccionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaccionAdapter.TransaccionViewHolder holder, int position) {
        Transaccion transaccion = listaTransacciones.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String fechaAdquision = sdf.format(transaccion.getFechaAdquisicion());
        holder.binding.tvItemNombre.setText(transaccion.getNombre());
        holder.binding.tvItemEstado.setText(transaccion.getEstado());
        holder.binding.tvItemFecha.setText(fechaAdquision);
        holder.binding.tvItemColeccion.setText(transaccion.getColeccionId());
        holder.binding.tvItemPedidoA.setText(transaccion.getEmailPedidoPor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(transaccion);
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

    public class TransaccionViewHolder extends RecyclerView.ViewHolder {
        private TransaccionLayoutBinding binding;
        public TransaccionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TransaccionLayoutBinding.bind(itemView);

        }
    }
}
