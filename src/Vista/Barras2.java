/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Fmat.Framework.Modelo.ClaseModelo;
import Fmat.Framework.Modelo.InterfazObserver;
import Fmat.Framework.Vista.ClaseVista;
import Modelo.AdminVotos;
import Modelo.Candidato;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Oscar
 */
public class Barras2 extends ClaseVista {
    public AdminVotos adminVotos;
    
     DefaultCategoryDataset data = new DefaultCategoryDataset();        
     JFreeChart chart = ChartFactory.createBarChart(null, null, null, data, PlotOrientation.VERTICAL, true, true, true);
     ChartFrame frame = new ChartFrame("Candidatos", chart);   
   
    
    public Barras2(ClaseModelo modelo,int idEvento){
       super(modelo, idEvento);
       //adminVotos.agregar((InterfazObserver) modelo);
    }
    
   
    public void Inicializar() {            
        frame.pack();
        frame.setVisible(true);        
    }  
    

    @Override
    public void mostrarInformacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar(Object obj) {
        ArrayList<Candidato> candidatos = (ArrayList<Candidato>) obj;
        for(Candidato candidato: candidatos){
            data.setValue(candidato.getNumVotos(), candidato.getNombre(), "Votos");
        }
        Inicializar();
    }
    
    public void cerrar(){
       frame.dispose();
    }
}
