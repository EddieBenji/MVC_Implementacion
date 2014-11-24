/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminVotos;

/**
 *
 * @author Vanessa
 */
public class ControladorSesion extends ClaseControlador {

    public ControladorSesion(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
    }
    
    public boolean iniciarSesion(String usuario, String clave){
        return ((AdminVotos)super.getModelo()).iniciarSesion(usuario, clave);
    }
    public void cerrarSesion(){
        ((AdminVotos)super.getModelo()).cerrarSesion();
    }
    public void agregarCuenta(String usuario, String clave){
        ((AdminVotos)super.getModelo()).agregarCuenta(usuario, clave);
    }
    @Override
    public void actualizar(Object obj) {
        //no implementado
    }
    
}
