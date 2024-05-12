package com.patob.loginperfilconfoto.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.patob.loginperfilconfoto.Modelo.Usuario;
import com.patob.loginperfilconfoto.Request.ApiClient;
import com.patob.loginperfilconfoto.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {

    private Context context;
    private ApiClient api;
    public MainActivityViewModel(@NonNull Application application) {

        super(application);
        context = application.getApplicationContext();
        api = new ApiClient();
    }

    public void acceso(String mail, String password){
        Usuario usuario = api.login(context, mail, password);
        if(usuario != null){
            Intent intent = new Intent(context, RegistroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("flag", 1);
            context.startActivity(intent);
        }  else {
            Toast.makeText(context, "Email o password incorrecto.", Toast.LENGTH_LONG).show();
        }
    }

    public void registro(){
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
