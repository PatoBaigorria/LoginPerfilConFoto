package com.patob.loginperfilconfoto.Request;

import android.content.Context;
import android.widget.Toast;

import com.patob.loginperfilconfoto.Modelo.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ApiClient {
    public void guardar(Context context, Usuario usuario){
        File archivo = new File(context.getFilesDir(), "datos.dat");
        try {
            FileOutputStream fo = new FileOutputStream(archivo);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(bo);
            oos.writeObject(usuario);
            bo.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error al acceder al archivo", Toast.LENGTH_LONG).show();
        }
    }

    public Usuario leer(Context context){
        File archivo = new File(context.getFilesDir(), "datos.dat");
        if(!archivo.exists()){
            return null;
        }
        Usuario usuario = null;
        try {
            FileInputStream fi = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario)ois.readObject();
            ois.close();
            fi.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public Usuario login(Context context, String email, String password){
        Usuario usuario = leer(context);
        if(usuario.getMail().equals(email) && usuario.getPassword().equals(password)){
            return usuario;
        }
        return null;
    }

}
