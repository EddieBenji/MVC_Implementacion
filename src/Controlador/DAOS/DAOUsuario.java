/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.DAOS;

import DAO.DAOBD;
import Modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lalo
 */
public class DAOUsuario extends DAOBD {

    @Override
    public String obtenerCondicionElemento(Object elemento) {

        int idUsuario = ((Usuario) elemento).getID();
        String condicion = "usuario_id = " + idUsuario;

        return condicion;
    }

    @Override
    public Object obtenerElementoDeTabla(ResultSet resultadoDeConsulta) {
        try {
            return new Usuario(resultadoDeConsulta.getInt("usuario_id"),
                    resultadoDeConsulta.getString("nombre"), resultadoDeConsulta.getString("password"),
                    resultadoDeConsulta.getString("rol"), resultadoDeConsulta.getString("permiso"));
        } catch (SQLException ex) {
            System.out.println("ERROR EN LA LEÍDA DE LA BD.");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateElement(Object elemento, String condicion) throws SQLException {

        this.establishConnection();

        Usuario elementoAmodificar = (Usuario) elemento;

        Statement sentencia = this.getConnection().createStatement();

        int actualizaUsuario = sentencia.
                executeUpdate("UPDATE mvcdb.usuario SET "
                        + "`usuario_id` = '" + elementoAmodificar.getID() + "'"
                        + ",`nombre` = '" + elementoAmodificar.getNombreUsuario() + "'"
                        + ",`password` = '" + elementoAmodificar.getPassword() + "'"
                        + ",`rol` = '" + elementoAmodificar.getRol() + "'"
                        + ",`permiso` = '" + elementoAmodificar.getPermisos() + "'"
                        + " WHERE " + condicion);
        this.closeConnection();
        return (actualizaUsuario != 0);
    }

    @Override
    public Object findElement(String nombreTabla, String condicion) throws SQLException {
        this.establishConnection();
        String query = "SELECT * FROM " + nombreTabla + " WHERE " + condicion;

        Statement sentenciaBuscaliente = this.getConnection().createStatement();
        ResultSet busquedaCliente = sentenciaBuscaliente.executeQuery(query);
        busquedaCliente.next();

        Usuario unUsuario = new Usuario(busquedaCliente.getInt("usuario_id"),
                busquedaCliente.getString("nombre"),
                busquedaCliente.getString("password"),
                busquedaCliente.getString("rol"),
                busquedaCliente.getString("permiso"));
        this.closeConnection();
        return unUsuario;
    }
}