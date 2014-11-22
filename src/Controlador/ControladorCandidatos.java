package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminVotos;

/**
 *
 * @author Lalo
 */
public class ControladorCandidatos extends ClaseControlador {

    private String nombre = this.getClass().getName();

    public ControladorCandidatos(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    public void agregarCandidato(int id, String candidato) {
        ((AdminVotos) super.getModelo()).agregarCandidatos(id, candidato);
    }

    public void eliminarCandidato(String candidato) {
        ((AdminVotos) super.getModelo()).eliminarCandidatos(candidato, this.nombre);
    }

    @Override
    public void actualizar(Object obj) {
        //Si queremos, se puede implementar, sino, pues no. (:
    }

}
