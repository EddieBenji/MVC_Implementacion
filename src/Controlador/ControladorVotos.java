package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminCandidato;
import Modelo.Candidato;
import Vista.VentanaPrincipal;
import java.util.ArrayList;

/**
 *
 * @author Eduardo Canché
 */
public class ControladorVotos extends ClaseControlador {

    ControladorSesion controlSesion = new ControladorSesion(((AdminCandidato) super.getModelo()), 0);
    VentanaPrincipal ventanaPrincipal;

    public ControladorVotos(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
        ventanaPrincipal = VentanaPrincipal.getInstance(this, controlSesion);
    }

    public void realizarVotacion(int idCandidato) {
        (AdminCandidato.getInstance()).agregarVoto(idCandidato);
    }

    @Override
    public void actualizar(Object obj) {
        actualizarVentana();
    }

    public void actualizarVentana() {
        ventanaPrincipal.iniciarDatosEnVentana((ArrayList<Candidato>) modelo.getDatos());
        ventanaPrincipal.setVisible(true);

    }

    /**
     * Habilita el botón de agregar o eliminar candidatos, dependiendo del rol
     * que tenga.
     *
     * @param permitido
     */
    public void permitirModificaciones(boolean permitido) {
        ventanaPrincipal.btnAgregar.setEnabled(permitido);
        ventanaPrincipal.txtNombre.setEnabled(permitido);
        ventanaPrincipal.btnEliminar.setEnabled(permitido);
        ventanaPrincipal.comboCandidatosEliminar.setEnabled(permitido);
    }

}
