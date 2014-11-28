/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ControladorCache;
import Clases.Shiro;
import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.awt.Window;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.jcs.access.exception.CacheException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author Lalo
 */
public class AdminUsuario extends ClaseModelo {

    private static AdminUsuario adminUsuario;
    private final ControladorCache cache;
    private static final int MAX_ELEMENTOS_CACHE = 1000;
    private final Shiro shiro;
    private static int id=501;

    public ArrayList observadores;

    private AdminUsuario() {
        super.datos = new ArrayList();
        observadores = new ArrayList();
        cache = ControladorCache.getInstanciaCache();
        try {

            cache.configLoad();
        } catch (CacheException ex) {
            System.out.println("Error con la inicialización de la caché");
            ex.printStackTrace();
        }
        shiro = new Shiro();

        inicializarEventos();
        inicializarRoles();
        inicializarCuentas();

    }

    public static AdminUsuario getInstancia() {
        if (adminUsuario == null) {
            adminUsuario = new AdminUsuario();
        }
        return adminUsuario;
    }

    private void inicializarEventos() {
        for (int i = 0; i < 3; i++) {
            eventos.add(new ClaseEvento(i));
        }
    }

    private void inicializarRoles() {
        shiro.agregarRol("Admin");
        shiro.agregarRol("Votante");
    }

    private void inicializarCuentas() {
         shiro.agregarCuenta("ed", "1", "Admin");
        Usuario usuario1=new Usuario(id,"ed","1","Admin");
        id++;
        try {
            cache.put(usuario1.getID(),usuario1);
            shiro.agregarCuenta("sel", "1", "Votante");
        Usuario usuario2=new Usuario(id,"sel", "1", "Votante");
        id++;
        cache.put(usuario2.getID(),usuario2);
        
        } catch (CacheException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean getRol(String rol) {
        return shiro.hasRol(rol);
    }

    public boolean iniciarSesion(String usuario, String clave) {
        boolean pudoEntrar = false;
        try {
            pudoEntrar = shiro.logIn(usuario, clave);
        } catch (UnknownAccountException uae) {

            JOptionPane.showMessageDialog(null, "No hay usuario con el nombre "
                    + usuario);
            uae.printStackTrace();
        } catch (IncorrectCredentialsException ice) {

            JOptionPane.showMessageDialog(null, "Password para la cuenta "
                    + clave + " es incorrecto");

            ice.printStackTrace();
        }

        return pudoEntrar;
    }

    public void cerrarSesion() {
        shiro.logOut();
        cerrarVentanas();
    }

    public void agregarCuenta(String usuario, String clave) {
        try {
            shiro.agregarCuenta(usuario, clave, "Votante");
            Usuario nuevo=new Usuario(id,usuario,clave,"Votante");
            id++;
                cache.put(nuevo.getID(),nuevo);
        } catch (CacheException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Usuario> obtenerUsuarios() {
        //declaramos el ArrayList que contendrá la información de los candidatos:
        ArrayList<Usuario> usuarios = new ArrayList<>();

        //recorremos toda la caché:
        for (int i = 501; i <= MAX_ELEMENTOS_CACHE; i++) {

            try {
                //obtenemos el candidato de la caché:
                Usuario unUsuario = (Usuario) cache.get(i);

                //si lo que devuelve la caché es nulo, entonces dejamos de recorrer
                //toda la caché.
                if (unUsuario == null) {
                    break;
                }

                usuarios.add(unUsuario);
            } catch (CacheException ex) {
                System.out.println("Error con la caché");
                ex.printStackTrace();
            }

        }
        /*Nótese que este ArrayList, fue llenado 
         con la información de la caché.*/
        return usuarios;
    }

    @Override
    public Object getDatos() {
        datos = obtenerUsuarios();
        return datos;

    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

}
