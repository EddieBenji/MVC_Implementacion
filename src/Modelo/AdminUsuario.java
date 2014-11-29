/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ControladorCache;
import Clases.ExcepcionArchivoConfiguracion;
import Clases.ExcepcionObjetoDesconocido;
import Clases.ExcepcionObjetoDuplicado;
import Clases.Shiro;
import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.awt.Window;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

/**
 *
 * @author Lalo
 */
public class AdminUsuario extends ClaseModelo {

    private static AdminUsuario adminUsuario;
    private final ControladorCache cache;
    private final Shiro shiro;
    private static int contadorUsuario = 501;

    private AdminUsuario() {
        cache = ControladorCache.getInstanciaCache();
        shiro = new Shiro();
        try {
            cache.configLoad();
            inicializarEventos();
            inicializarCuentas();

        } catch (ExcepcionArchivoConfiguracion ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
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

    private void inicializarCuentas() {
        inicializarRoles();
        //Se agrega: usuario, contraseña, rol
        registrarCuenta("ed", "1", "Admin","*");
        //Agregamos otra cuenta, pero con el rol de Votante:
        registrarCuenta("sel", "1");
    }

    private void inicializarRoles() {
        shiro.agregarRol("Admin","*");
        shiro.agregarRol("Votante","Votar");
    }

    /**
     * Registra una nueva cuenta en el shiro y en la caché, con los datos
     * especificados.
     *
     * @param usuario
     * @param clave
     * @param rol
     * @param permisos
     */
    public void registrarCuenta(String usuario, String clave, String rol,String permisos) {
        try {

            shiro.agregarCuenta(usuario, clave, rol);
            Usuario nuevoUsuario = new Usuario(contadorUsuario, usuario, clave, rol,permisos);
            cache.put(nuevoUsuario);
            contadorUsuario++;
            //FALTA A LA BD.
        } catch (ExcepcionObjetoDuplicado ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
    }

    /**
     **Registra una nueva cuenta en el shiro y en la caché, con los datos
     * especificados, pero con el rol de VOTANTE sin opción a otro Rol.
     *
     * @param usuario
     * @param clave
     */
    public void registrarCuenta(String usuario, String clave) {
        try {

            shiro.agregarCuenta(usuario, clave, "Votante");
            //la caché necesita un objeto!
            Usuario nuevoUsuario = new Usuario(contadorUsuario, usuario, clave, "Votante","Votar");
            cache.put(nuevoUsuario);
            contadorUsuario++;
            //FALTA A LA BD.
        } catch (ExcepcionObjetoDuplicado ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
    }

    public boolean getRol(String rol) {
        return shiro.hasRol(rol);
    }
    
    public boolean getPermiso(String permiso){
        return shiro.hasPermisos(permiso);
    }
    

    public boolean iniciarSesion(String usuario, String clave) {
        boolean pudoEntrar = false;
        try {
            pudoEntrar = shiro.logIn(usuario, clave);
        } catch (UnknownAccountException uae ) {

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

    private ArrayList<Usuario> obtenerUsuarios() {
        //declaramos el ArrayList que contendrá la información de los candidatos:
        ArrayList<Usuario> usuarios = new ArrayList<>();

        //recorremos toda la caché:
        for (int i = 501; i <= contadorUsuario; i++) {

            try {
                Usuario unUsuario = (Usuario) cache.get(i);
                usuarios.add(unUsuario);
            } catch (ExcepcionObjetoDesconocido ex) {
                System.out.println("Error:");
                ex.printStackTrace();
            }

        }
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
