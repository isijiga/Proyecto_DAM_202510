package com.example.proyecto_dam_202510.data.viewdata;

import static com.google.firebase.firestore.AggregateField.sum;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Clase que sirve para obtener de la base de datos los datos globales de la applicación para la pantalla de estadistica
 * Esta clase cuando contacta con la base de datos lo hace por la clase especial de Firebase AggregateQuery. El motivo es eficiencia
 * y el coste de procesamiento es menor que si se hace por get().
 */

public class Estadisticas_vm extends ViewModel {

    private FirebaseFirestore db;
    private MutableLiveData<Long> countColecciones = new MutableLiveData<>(0L);
    private MutableLiveData<Long> countUsuarios = new MutableLiveData<>(0L);
    private MutableLiveData<Long> countUsers_Colecciones = new MutableLiveData<>(0L);
    private MutableLiveData<Double> sumPrecio = new MutableLiveData<>();
    private MutableLiveData<Long> sum_cartasPorSobre = new MutableLiveData<>(0L);
    private MutableLiveData<Long> countCromosPosesion = new MutableLiveData<>(0L);
    private MutableLiveData<Double> precioPorCarta = new MutableLiveData<>();

    public MutableLiveData<Long> getCountCromosPosesion() {
        return countCromosPosesion;
    }

    public MutableLiveData<Long> getSum_cartasPorSobre() {
        return sum_cartasPorSobre;
    }

    public MutableLiveData<Double> getPrecioPorCarta() {
        return precioPorCarta;
    }

    public MutableLiveData<Double> getSumPrecio() {
        return sumPrecio;
    }

    public MutableLiveData<Long> getCountUsuarios() {
        return countUsuarios;
    }

    public MutableLiveData<Long> getCountUsers_Colecciones() {
        return countUsers_Colecciones;
    }

    public MutableLiveData<Long> getCountColecciones() {
        return countColecciones;
    }


    public void cargarEstadisticas() {
        db = FirebaseFirestore.getInstance();
        /**/
        AggregateQuery conteo_Users = db.collection("users").
                count();
        AggregateQuery conteo_Coleccioens = db.collection("colecciones").
                count();
        AggregateQuery conteo_Users_Colecciones = db.collection("users_colecciones").
                count();
        AggregateQuery suma_precio = db.collection("colecciones").aggregate(sum("coste"));
        Task<AggregateQuerySnapshot> tareaSumaPrecio = suma_precio.get(AggregateSource.SERVER);

        AggregateQuery suma_cartasPorSobre = db.collection("colecciones").aggregate(sum("cartasPorSobre"));
        Task<AggregateQuerySnapshot> tareaSumaCartasPorSobre = suma_cartasPorSobre.get(AggregateSource.SERVER);

        AggregateQuery count_cromosPosesion = db.collectionGroup("cromosPosesion").count();
        Task<AggregateQuerySnapshot> tareaCountCromosPosesion = count_cromosPosesion.get(AggregateSource.SERVER);

        /**
         * para que no se solapen las tareas y lleguen unas antes que otras (provoca errores al dividir) se hace uso de Tasks.whenAllSuccess
         * espera a que todas las tareas terminen y ejecuta el metodo onSuccess.
         */
        Tasks.whenAllSuccess(tareaSumaPrecio, tareaSumaCartasPorSobre, tareaCountCromosPosesion).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
            @Override
            public void onSuccess(List<Object> objects) {
                AggregateQuerySnapshot snapshot0 = (AggregateQuerySnapshot) objects.get(0);
                AggregateQuerySnapshot snapshot1 = (AggregateQuerySnapshot) objects.get(1);
                AggregateQuerySnapshot snapshot2 = (AggregateQuerySnapshot) objects.get(2);
                Double precio = (Double) snapshot0.get(sum("coste"));
                Long cartasPorSobre = (Long) snapshot1.get(sum("cartasPorSobre"));
                Long cromosPosesion = (Long) snapshot2.getCount();

                precioPorCarta.setValue((precio / cartasPorSobre) * cromosPosesion);
            }
        });


        conteo_Users.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countUsuarios.setValue(aggregateQuerySnapshot.getCount());
            }
        });

        conteo_Coleccioens.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countColecciones.setValue(aggregateQuerySnapshot.getCount());
            }
        });
        conteo_Users_Colecciones.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countUsers_Colecciones.setValue(aggregateQuerySnapshot.getCount());
            }
        });
        suma_precio.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                sumPrecio.setValue((Double) aggregateQuerySnapshot.get(sum("coste")));
            }
        });

        count_cromosPosesion.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                countCromosPosesion.setValue(aggregateQuerySnapshot.getCount());
            }
        });
        suma_cartasPorSobre.get(AggregateSource.SERVER).addOnSuccessListener(new OnSuccessListener<AggregateQuerySnapshot>() {
            @Override
            public void onSuccess(AggregateQuerySnapshot aggregateQuerySnapshot) {
                sum_cartasPorSobre.setValue((Long) aggregateQuerySnapshot.get(sum("cartasPorSobre")));
            }
        });
    }

}
