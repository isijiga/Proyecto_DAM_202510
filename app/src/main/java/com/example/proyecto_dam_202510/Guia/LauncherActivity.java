package com.example.proyecto_dam_202510.Guia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_dam_202510.MainActivity;
import com.example.proyecto_dam_202510.R;

public class LauncherActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "LauncherActivity";
    private static final String KEY_FIRST_LAUNCH = "primera_vez";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        boolean primeravez = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH,true);
        if (primeravez){
            startActivity(new Intent(LauncherActivity.this,GuiaActivity.class));
        }
        else{
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        }

        finish();

    }
}