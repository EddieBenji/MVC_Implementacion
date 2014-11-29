package Controlador;

import Controlador.DAOS.DAOCandidato;
import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminCandidato;
import Modelo.Candidato;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lalo
 */
public class ControladorCandidatos extends ClaseControlador {

    private String nombre = this.getClass().getName();
    private DAOCandidato daoCan = new DAOCandidato();

    public ControladorCandidatos(ClaseModelo modelo, int idEvento) {
        
        super(modelo, idEvento);
       // System.out.println("entre1");
       
        //System.out.println("entre2 ");
        try {
            System.out.println(daoCan.getConnection());
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }   
    
    public void addCandidato(Candidato cand){
        try {
            daoCan.addElement(cand);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCandidatos.class.getName()).log(Level.SEVERE, null, ex);
        }
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
