/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Fmat.Framework.Modelo.ClaseModelo;
import Fmat.Framework.Vista.ClaseVista;
import Modelo.AdminCandidato;
import Modelo.Candidato;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Oscar
 */
public class Pastel extends ClaseVista {
    
    public AdminCandidato adminVotos;
    private static Pastel pastel2;
    private ArrayList<Candidato> candidatos;
    
    private static DefaultPieDataset data;
    private static JFreeChart chart;
    private static ChartFrame frame;  
    
    public Pastel(ClaseModelo modelo,int idEvento){
       super(modelo, idEvento);       
       initData(); 
    }
    
     private static void initData() {
        data = new DefaultPieDataset();
        chart = ChartFactory.createPieChart3D("Candidatos", data);
        frame = new ChartFrame("Candidatos", chart);  
        //adminVotos.agregar((InterfazObserver) modelo);
    }
     
    public static Pastel getInstance(ClaseModelo modelo,int idEvento) {
        if (pastel2 == null) {
            pastel2 = new Pastel(modelo,idEvento);
        }
        eraseData();
        initData();
        return pastel2;
    }

    private static void eraseData() {
        data = null;
        chart = null;
        frame = null;
    } 

    @Override
    public void mostrarInformacion() {
        frame.pack();
        frame.setVisible(true); 
    }

    @Override
    public void actualizar(Object o) {
        candidatos = (ArrayList<Candidato>) o;
        for(Candidato candidato: candidatos){
            data.setValue(candidato.getNombre(), candidato.getNumVotos() );
        }
        mostrarInformacion();
    }
     
     
    
}
