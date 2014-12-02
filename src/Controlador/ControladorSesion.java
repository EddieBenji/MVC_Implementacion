package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminUsuario;

/**
 *
 * @author Vanessa Fragoso
 */
public class ControladorSesion extends ClaseControlador {

    public ControladorSesion( ClaseModelo modelo, int idEvento ) {
        super( modelo, idEvento );
    }

    public boolean iniciarSesion( String usuario, String clave ){
        return ( AdminUsuario.getInstancia() ).iniciarSesion( usuario, clave );
    }
    public void cerrarSesion(){
        ( AdminUsuario.getInstancia() ).cerrarSesion();
    }
    public void agregarCuenta( String usuario, String clave ){
        //cualquier usuario que se agregue con la aplicación
        //se le pone el rol de votante
        ( AdminUsuario.getInstancia() ).registrarCuenta( usuario, clave ,"Votante","Votar");
    }

    @Override
    public void actualizar( Object obj ) {}

}
