package com.patob.loginperfilconfoto.ui.registro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.patob.loginperfilconfoto.Modelo.Usuario;
import com.patob.loginperfilconfoto.databinding.ActivityRegistroBinding;
import androidx.annotation.Nullable;

public class RegistroActivity extends AppCompatActivity {
    private ActivityRegistroBinding binding;
    private RegistroActivityViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);
        vm.getmUsuario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                if (usuario != null){
                    binding.etDni.setText(usuario.getDni());
                    binding.etApellido.setText(usuario.getApellido());
                    binding.etNombre.setText(usuario.getNombre());
                    binding.etEmail.setText(usuario.getMail());
                    binding.etPass.setText(usuario.getPassword());
                    vm.cargarFoto();
                }
            }
        });
        Intent intent = getIntent();
        vm.cargarDatos(intent);
        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = binding.etDni.getText().toString();
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPass.getText().toString();
                vm.guardar(dni, nombre, apellido, email, password);
            }
        });
        vm.getmFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.ivFoto.setImageBitmap(bitmap);
            }
        });
        binding.btCargarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.respuestaCamara(requestCode, resultCode, data, 1);
    }
}