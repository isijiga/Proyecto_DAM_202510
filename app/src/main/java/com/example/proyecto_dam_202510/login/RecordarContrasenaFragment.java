package com.example.proyecto_dam_202510.login;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.proyecto_dam_202510.R;
import com.example.proyecto_dam_202510.databinding.FragmentRecordarContrasenaBinding;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Fragmento que se encarga de enviar al usuario una nueva contraseña por olvido. Recibe un correo
 * electronico con un enlace para poder cambiar la pass.
 */
public class RecordarContrasenaFragment extends Fragment {
    FragmentRecordarContrasenaBinding binding;
    public RecordarContrasenaFragment() {
    }

    public static RecordarContrasenaFragment newInstance(String param1, String param2) {
        RecordarContrasenaFragment fragment = new RecordarContrasenaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecordarContrasenaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * cuando se pulsa el boton, se solicita una nueva pass a traves del metodo <code>sendPasswordResetEmail</code>
         */
     binding.button2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             FirebaseAuth.getInstance().sendPasswordResetEmail(binding.etUsuario.getText().toString());
             binding.tvUsuario.setText("Correo enviado");
             binding.etUsuario.setEnabled(false);

             Toast.makeText(getContext(),"Correo enviado", Toast.LENGTH_LONG).show();
/**
 * se vuelve al fragmento LoginFragment
 */
             getParentFragmentManager().beginTransaction()
                     .replace(R.id.fragmentContainerView, new LoginFragment())
                     .addToBackStack(null)
                     .commit();
         }
     });
    }
}