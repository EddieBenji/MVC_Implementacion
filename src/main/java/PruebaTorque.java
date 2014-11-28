/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.torque.TorqueException;
import org.apache.torque.tutorial.om.Candidato;



/**
 *
 * @author Lalo
 */
public class PruebaTorque {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Candidato candidato = new Candidato();
            candidato.setNombre("Oscar");
            candidato.setNumVotos(0);
            candidato.save();
        } catch (TorqueException ex) {
            System.out.println("ERRORRRR!!!!");
            ex.printStackTrace();
        }
        
    }
    
}
