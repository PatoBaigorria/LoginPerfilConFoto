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
            FileOutputStream fo = new FileOutputStream(archivo, false);
            BufferedOutputStream bo = new BufferedOutputStream(fo);
            ObjectOutputStream oos = new ObjectOutputStream(bo);
            oos.writeObject(usuario);
            bo.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "Error al acceder al archivo", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(context, "Error al acceder al archivo", Toast.LENGTH_LONG).show();
        }
    }

    public Usuario leer(Context context){
        File archivo = new File(context.getFilesDir(), "datos.dat");
        Usuario usuario = null;
        try {
            FileInputStream fi = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public Usuario login(Context context, String mail, String password){
        File archivo = new File(context.getFilesDir(), "datos.dat");

        try {
            FileInputStream fi = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fi);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Usuario usuario = (Usuario) ois.readObject();
            if(usuario.getMail().equals(mail) && usuario.getPassword().equals(password)){
                return usuario;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
