package Vista;

import Fmat.Framework.Modelo.ClaseModelo;
import Fmat.Framework.Vista.ClaseVista;
import Modelo.Candidato;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Numeros extends ClaseVista {

    private VentanaTabla tablaCandidatos = new VentanaTabla();
    private ArrayList<Candidato> candidatos;

    public Numeros(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
    }

    @Override
    public void actualizar(Object o) {
        //super.actualizar(o);
        this.candidatos = (ArrayList<Candidato>) o;

        mostrarInformacion();
    }

    @Override
    public void mostrarInformacion() {
        tablaCandidatos.llenaTabla(candidatos);
        tablaCandidatos.setVisible(true);
    }
}
