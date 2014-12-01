package Controlador.DAOS;

import DAO.DAOBD;
import Modelo.Candidato;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Eduardo Canché
 */
public class DAOCandidato extends DAOBD {

    /**
     *Lo único que hace es devolver una cadena de la siguiente forma:
     * candidato_id = "idCandidato".
     * @param elemento es el objeto del que se tomará la condición.
     * @return
     */
    @Override
    public String obtenerCondicionElemento( Object elemento ) {

        int idCandidato = ( (Candidato) elemento ).getID();
        String condicion = "candidato_id = " + idCandidato;

        return condicion;
    }

    @Override
    public Object obtenerElementoDeTabla( ResultSet resultadoDeConsulta ) {
        try {
            return new Candidato( resultadoDeConsulta.getInt( "candidato_id" ),
                    resultadoDeConsulta.getString( "nombre" ), resultadoDeConsulta.getInt( "num_votos" ) );
        } catch ( SQLException ex ) {
            System.out.println( "ERROR EN LA LEÍDA DE LA BD." );
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateElement( Object elemento, String condicion ) throws SQLException {

        this.establishConnection();

        Candidato elementoAmodificar = ( Candidato ) elemento;

        Statement sentencia = this.getConnection().createStatement();

        int actualizaCandidato = sentencia.
                executeUpdate("UPDATE mvcdb.candidato SET "
                        + "`candidato_id` = '" + elementoAmodificar.getID() + "'"
                        + ",`nombre` = '" + elementoAmodificar.getNombre() + "'"
                        + ",`num_votos` = '" + elementoAmodificar.getNumVotos() + "'"
                        + " WHERE " + condicion);

        return ( actualizaCandidato != 0 );
    }

    @Override
    public Object findElement( String nombreTabla, String condicion ) throws SQLException {
        this.establishConnection();

        String query                   = "SELECT * FROM " + nombreTabla + " WHERE " + condicion;
        Statement sentenciaBuscaliente = this.getConnection().createStatement();
        ResultSet busquedaCliente      = sentenciaBuscaliente.executeQuery( query );

        busquedaCliente.next();

        Candidato unCandidato = new Candidato( busquedaCliente.getInt( "candidato_id" ),
                busquedaCliente.getString( "nombre" ),
                busquedaCliente.getInt( "num_votos" ) );

        return unCandidato;
    }

}
