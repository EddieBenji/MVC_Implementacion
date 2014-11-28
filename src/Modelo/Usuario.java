package Modelo;

import Clases.Cacheable;

/**
 *
 * @author Lalo
 */
public class Usuario implements Cacheable, java.io.Serializable{
    
    private int idUsuario;
    private String nombreUsuario;
    private String password ;
    private String rol ;

    public Usuario() {}

    
    public Usuario(int idUsuario, String nombreUsuario, String password, String rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.rol = rol;
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
    
    
    
}
