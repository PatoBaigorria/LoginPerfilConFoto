package com.patob.loginperfilconfoto.ui.login;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import com.patob.loginperfilconfoto.databinding.ActivityMainBinding;







public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel vm;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);
        binding.etMail.requestFocus();

        solicitarPermiso();

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = binding.etMail.getText().toString();
                String password = binding.etPassword.getText().toString();
                vm.acceso(mail, password);
                binding.etMail.setText("");
                binding.etPassword.setText("");
            }
        });

        binding.btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vm.registro();
            }
        });
    }

    public void solicitarPermiso(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && (checkSelfPermission(Manifest.permission.CAMERA)  != PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)  != PackageManager.PERMISSION_GRANTED)){
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
    }
}