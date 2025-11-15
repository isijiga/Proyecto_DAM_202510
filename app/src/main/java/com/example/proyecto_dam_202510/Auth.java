package com.example.proyecto_dam_202510;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class Auth  extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    public Auth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        this.db = FirebaseFirestore.getInstance();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
                     super.onStart();
                     FirebaseUser currentUser = mAuth.getCurrentUser();
                     if (currentUser != null) {
                         reload();
                     }

    }
    public void createAccount(String mail, String pass, Context context ){
        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "UsuarioCreado!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Funciones.crearUsuario(user,context,db);


                        }
                        else {
                            Log.w(TAG, "Fallo al crear el usuario", task.getException());
                            Toast.makeText(context, "Fallo al crear el usuario :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });




    }

    public void signin(String mail, String pass,AuthListener listener,Context context ){
        mAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Log.d(TAG, "signIn:Correcto");
                       FirebaseUser user = mAuth.getCurrentUser();
                       if (listener != null) {
                           listener.onAuthSuccess();
                       }

                   }

                   else {
                       Toast.makeText(context,"Contraseña Incorrecta",Toast.LENGTH_LONG).show();
                       Log.w(TAG, "signIn:Fallo", task.getException());

                    }
                }});



    }


    public interface AuthListener {
        void onAuthSuccess();
        void onAuthFailure(String errorMessage);
    }

    private void reload() {

    }
}
