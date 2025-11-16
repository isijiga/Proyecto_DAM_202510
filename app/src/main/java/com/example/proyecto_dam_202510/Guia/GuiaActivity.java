package com.example.proyecto_dam_202510.Guia;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.ActivityGuiaBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
                editor.putBoolean("primera_vez",false);
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