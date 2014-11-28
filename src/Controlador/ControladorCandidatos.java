package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminCandidato;

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
        ((AdminCandidato) super.getModelo()).agregarCandidatos(id, candidato);
    }

    public void eliminarCandidato(String candidato) {
        ((AdminCandidato) super.getModelo()).eliminarCandidatos(candidato, this.nombre);
    }

    @Override
    public void actualizar(Object obj) {
        //Si queremos, se puede implementar, sino, pues no. (:
    }

}
