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
public class Barras extends ClaseVista {
    
    public AdminVotos adminVotos;
    private static Barras barras2;
    private ArrayList<Candidato> candidatos;
    
    private static DefaultCategoryDataset data;        
    private static JFreeChart chart;
    private static ChartFrame frame;   
   
    
    public Barras(ClaseModelo modelo,int idEvento){
       super(modelo, idEvento);       
       iniciarDatos(); 
    }

    private static void iniciarDatos() {
        data = new DefaultCategoryDataset();
        chart = ChartFactory.createBarChart(null,
                null,
                null,
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);
        frame = new ChartFrame("Candidatos", chart);
    }
    
    public static Barras getInstance(ClaseModelo modelo,int idEvento) {
        if (barras2 == null) {
            barras2 = new Barras(modelo,idEvento);
        }
        borrarDatos();
        iniciarDatos();
        return barras2;
    }

    private static void borrarDatos() {
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
            data.setValue(candidato.getNumVotos(), candidato.getNombre(), "Votos");
        }
        mostrarInformacion();
    }
    
    
}
