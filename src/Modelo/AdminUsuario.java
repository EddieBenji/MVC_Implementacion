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
import Controlador.DAOS.DAOUsuario;
import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.awt.Window;
import java.sql.SQLException;
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
    private final Shiro shiro;

    private DAOUsuario daoUsuario;

    private static int contadorUsuario = 501;

    private AdminUsuario() {
        cache = ControladorCache.getInstanciaCache();
        shiro = new Shiro();

        daoUsuario = new DAOUsuario();

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
        /*
        registrarCuenta("ed","1", "Admin", "*");
        registrarCuenta("sel","1");
        */
        llenarCacheyShiro();
        
        
        
    }
    
    private void llenarCacheyShiro(){
        try {
            cache.limpiarCache();
            
            for (Object unUsuario : daoUsuario.getAllFromTable("usuario")) {
                try {
                    contadorUsuario++;
                    cache.put((Usuario) unUsuario);
                    shiro.agregarCuenta(((Usuario) unUsuario).getNombreUsuario(),
                            ((Usuario) unUsuario).getPassword(),
                            ((Usuario) unUsuario).getRol());
                } catch (ExcepcionObjetoDuplicado ex) {
                    System.out.println("Error");
                    ex.printStackTrace();
                }
            }
        } catch (CacheException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicializarRoles() {
        shiro.agregarRol("Admin", "*");
        shiro.agregarRol("Votante", "Votar");
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
    public void registrarCuenta(String usuario, String clave, String rol, String permisos) {
        try {
            String claveEncriptada = shiro.encriptar(clave);
            shiro.agregarCuenta(usuario, claveEncriptada, rol);
            Usuario nuevoUsuario = new Usuario(contadorUsuario, usuario, claveEncriptada, rol, permisos);
            cache.put(nuevoUsuario);
            daoUsuario.addElement(nuevoUsuario);

            contadorUsuario++;
        } catch (ExcepcionObjetoDuplicado | SQLException ex) {
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

            String claveEncriptada = shiro.encriptar(clave);
            shiro.agregarCuenta(usuario, claveEncriptada, "Votante");
            //la caché necesita un objeto!
            Usuario nuevoUsuario = new Usuario(contadorUsuario, usuario, claveEncriptada, "Votante", "Votar");
            cache.put(nuevoUsuario);
            daoUsuario.addElement(nuevoUsuario);
            contadorUsuario++;
        } catch (ExcepcionObjetoDuplicado | SQLException ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
    }

    public boolean getRol(String rol) {
        return shiro.hasRol(rol);
    }

    public boolean getPermiso(String permiso) {
        return shiro.hasPermisos(permiso);
    }

    public boolean iniciarSesion(String usuario, String clave) {
        boolean pudoEntrar = false;
        llenarCacheyShiro();
        AdminCandidato.getInstance().llenarCache();
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
        try {
            cache.limpiarCache();
            shiro.logOut();
            cerrarVentanas();
        } catch (CacheException ex) {
            Logger.getLogger(AdminUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getDatos() {
        datos = daoUsuario.getAllFromTable("usuario");
        return datos;

    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

}
