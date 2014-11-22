package Vista;

import Fmat.Framework.Modelo.ClaseModelo;
import Fmat.Framework.Vista.ClaseVista;
import Modelo.Candidato;
import java.util.ArrayList;


/**
 *Esta clase deberá estar en el nuevo proyecto (el que se implementará).
 * @author a09001005
 */
public class Pastel extends ClaseVista{
    private final GraficaPastel graficaPastel;
    private ArrayList<Candidato> candidatos;
    
    public Pastel(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
        this.candidatos=new ArrayList();
        graficaPastel=new GraficaPastel();
    }

    @Override
    public void actualizar(Object datos) {
        this.candidatos= (ArrayList<Candidato>) datos;
        mostrarInformacion();
    }

    @Override
    public void mostrarInformacion() {
        graficaPastel.init(candidatos);
        graficaPastel.setVisible(true);
    }

 
}
