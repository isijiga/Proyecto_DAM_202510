package com.example.proyecto_dam_202510.Guia;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.databinding.ActivityGuiaBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * @author Isidoro Jiménez García
 * Actividad principal para la guia de uso de la app. En el metodo onCreate un TabLayoutMediator para seguir el
 * avance de la guia y un boton para cerrarla durante la ejecucion. En este momento se establece el valor 'false'
 * a la key 'primera_vez' del SharedPreferences guardado en el dispositivo del usuario, de tal manera
 * que solo aparezca solamente la primera vez de uso.
 *
 */
public class GuiaActivity extends AppCompatActivity {
    private static final String PREFERENCES_NAME = "LauncherActivity";
    ActivityGuiaBinding binding;
    GuiaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new GuiaAdapter(this);
        binding = ActivityGuiaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPagerGuia.setAdapter(adapter);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("primera_vez", false);
                editor.apply();
                Intent intent = new Intent(GuiaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new TabLayoutMediator(binding.tableLayout, binding.viewPagerGuia, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();
    }


}