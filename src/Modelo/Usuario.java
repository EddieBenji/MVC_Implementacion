package Modelo;

import Clases.Cacheable;

/**
 *
 * @author Lalo
 */
public class Usuario implements Cacheable {

    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private String rol;
    private String permisos;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String password, String rol, String permisos) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
        this.permisos = permisos;
    }

    @Override
    public int getID() {
        return idUsuario;
    }

    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the permisos
     */
    public String getPermisos() {
        return permisos;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @param permisos the permisos to set
     */
    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    /**
     *
     * @param permisos
     */
    public void agregarPermisos(String permisos){
        this.permisos += permisos;
        
    }

    @Override
    public String toString() {
        //return getID()+"', '"+getNombre()+"', '"+getNumVotos();
        return idUsuario + ", '" + nombreUsuario + "', '" + 
                password + "', '" + rol + "', '" + permisos +"'";
    }
    
    
}
