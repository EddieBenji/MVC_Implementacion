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
    private static Numeros numeros;

    public Numeros(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
    }
    
    public static Numeros getInstance(ClaseModelo modelo,int idEvento) {
        if (numeros == null) {
            numeros = new Numeros(modelo,idEvento);
        }
        return numeros;
    }

    @Override
    public void actualizar(Object o) {
        this.candidatos = (ArrayList<Candidato>) o;
        mostrarInformacion();
    }

    @Override
    public void mostrarInformacion() {
        tablaCandidatos.llenaTabla(candidatos);
        tablaCandidatos.setVisible(true);
    }
}
