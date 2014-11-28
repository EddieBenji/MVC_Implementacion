/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.DAOS;

import DAO.DAOBD;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lalo
 */
public class DAOCandidato extends DAOBD{
    
    

    @Override
    public void establishConnection(String host, String port, String user, String password, String nameBD) {
        super.establishConnection(host, port, user, password, nameBD); 
    }

    @Override
    public int getIdElemento(Object elemento) {
       
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerClaveElemento(Object elemento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object obtenerElementoDeTabla(ResultSet resultadoDeConsulta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateElement(Object elemento, String condicion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object findElement(String condicion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
