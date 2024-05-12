package com.patob.loginperfilconfoto.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.patob.loginperfilconfoto.Modelo.Usuario;
import com.patob.loginperfilconfoto.Request.ApiClient;
import com.patob.loginperfilconfoto.ui.login.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistroActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Usuario> mUsuario;

    private MutableLiveData<Bitmap>mFoto;


    public RegistroActivityViewModel(@NonNull Application application) {

        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Usuario> getmUsuario() {
        if(mUsuario==null){
            mUsuario=new MutableLiveData<>();
        }
        return mUsuario;
    }
    public MutableLiveData<Bitmap> getmFoto() {
        if(mFoto==null){
            mFoto=new MutableLiveData<>();
        }
        return mFoto;
    }

    public void cargarDatos(Intent intent){
        ApiClient api = new ApiClient();
        int existe = intent.getFlags();
        Usuario usuario = new Usuario();
        if(existe != -1){
            usuario = api.leer(getApplication());
        }
        mUsuario.setValue(usuario);
    }

    public void guardar(String dni, String nombre, String apellido, String email, String password) {
        if(!dni.equals("") && !nombre.equals("") && !apellido.equals("") && !email.equals("") && !password.equals("")){
            Usuario usuario = new Usuario(dni, nombre, apellido, email, password, "foto.png");
            ApiClient api = new ApiClient();
            api.guardar(getApplication(), usuario);
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(intent);
        } else {
            Toast.makeText(getApplication(), "Debe ingresar datos en todos los campos", Toast.LENGTH_LONG).show();
        }
    }
    public void respuestaCamara(int requestCode, int resultCode, @Nullable Intent data, int REQUEST_IMAGE_CAPTURE){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            bitmap = rotarFoto(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte [] b = baos.toByteArray();
            File archivo = new File(context.getFilesDir(), "perfil.png");
            if (archivo.exists()){
                archivo.delete();
            }
            try{
                FileOutputStream fos = new FileOutputStream(archivo);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(b);
                bos.flush();
                bos.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            mFoto.setValue(bitmap);
        }
    }

    private Bitmap rotarFoto(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(270);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public void cargarFoto(){
        File archivo = new File(context.getFilesDir(), "perfil.png");
        if(!archivo.exists()){
            Toast.makeText(context, "El archivo no existe, seleccione una imagen para cargar la foto", Toast.LENGTH_LONG).show();
        } else {
            try {
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte b[];
                b = new byte[bis.available()];
                bis.read(b);
                Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
                mFoto.setValue(bm);
                bis.close();
                fis.close();
            } catch (FileNotFoundException e){
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
