package Vista;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Fmat.Framework.Modelo.ClaseModelo;
import Fmat.Framework.Vista.ClaseVista;
import Modelo.Candidato;
import java.util.ArrayList;

/**
 *
 * @author Carlos
 */
public class Barras extends ClaseVista{
    private ArrayList<Candidato> candidatos;
    private final GraficaBarras graficaBarras;
    
    public Barras(ClaseModelo modelo,int idEvento) {
        super(modelo,idEvento);
        this.candidatos= new ArrayList();
        this.graficaBarras=new GraficaBarras();
    }

    @Override
    public void actualizar(Object datos) {
        //super.actualizar(datos);
        this.candidatos= (ArrayList<Candidato>) datos;
        mostrarInformacion();
    }

    @Override
    public void mostrarInformacion() {
        graficaBarras.init(candidatos);
        graficaBarras.setVisible(true);      
    }
}
