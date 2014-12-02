/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ControladorCache;
import Clases.ExcepcionArchivoConfiguracion;
import Clases.ExcepcionObjetoDesconocido;
import Clases.ExcepcionObjetoDuplicado;
import Controlador.DAOS.DAOCandidato;
import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.awt.Window;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author Lalo
 */
public class AdminCandidato extends ClaseModelo {

    private static AdminCandidato adminVtos;
    private final ControladorCache cache;
    private DAOCandidato daoCandidato;

    private int contadorCandidatos = 0;

    private AdminCandidato() {
        cache = ControladorCache.getInstanciaCache();
        daoCandidato = new DAOCandidato();
        try {
            cache.configLoad();
            inicializarCandidatos();
            inicializarEventos();
        } catch (ExcepcionArchivoConfiguracion ex) {
            System.out.println("Error con la inicialización de la caché");
            ex.printStackTrace();
        }
    }

    /**
     * Devuelve la instancia de esta clase, con la intención de tener solo una
     * instancia.
     *
     * @return
     */
    public static AdminCandidato getInstance() {
        if (adminVtos == null) {
            adminVtos = new AdminCandidato();
        }
        return adminVtos;
    }

    /**
     * Inicializa en memoria 3 candidatos por default.
     */
    private void inicializarCandidatos() {

        llenarCache();

    }

    public void llenarCache() {
        try {
            //lo va a llenar de lo que hay en la BD.
            cache.limpiarCache();
            contadorCandidatos = 0;
            for (Object unCandidato : daoCandidato.getAllFromTable("candidato")) {
                try {
                    contadorCandidatos++;
                    cache.put((Candidato) unCandidato);
                } catch (ExcepcionObjetoDuplicado ex) {
                    ex.printStackTrace();
                }

            }
        } catch (CacheException ex) {
            ex.printStackTrace();
        }
    }

    private void inicializarEventos() {
        for (int i = 0; i < 3; i++) {
            eventos.add(new ClaseEvento(i));
        }
    }

    /**
     * Agrega un candidato, a la lista de candidatos.
     *
     * @param id
     * @param nombre
     */
    public void agregarCandidatos(int id, String nombre) {
        try {

            contadorCandidatos++;
            Candidato nuevoCandidato = new Candidato(id, nombre, 0);
            //lo mete a la BD:
            daoCandidato.addElement(nuevoCandidato);
            //lo mete a la caché.
            cache.put(nuevoCandidato);
            notificarObservadoresEvento(0);

        } catch (SQLException | ExcepcionObjetoDuplicado ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        } 
    }

    public void agregarVoto(int idCandidato) {
        try {
            Candidato unCandidato = (Candidato) cache.get(idCandidato);
            unCandidato.agregarVoto();

            actualizarCache(unCandidato);
            actualizarBD(unCandidato);

            notificarObservadoresEvento(0);
        } catch (ExcepcionObjetoDesconocido | ExcepcionObjetoDuplicado ex) {
            try {
                //entonces no está en la caché!
                Candidato unCandidato = (Candidato) daoCandidato.
                        findElement("candidato", "candidato_id = " + idCandidato);

                unCandidato.agregarVoto();

                actualizarBD(unCandidato);
                cache.put(unCandidato);

            } catch (SQLException | ExcepcionObjetoDuplicado e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidato.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private void actualizarCache(Candidato unCandidato) throws ExcepcionObjetoDesconocido, ExcepcionObjetoDuplicado {
        cache.delete(unCandidato.getID());
        cache.put(unCandidato);
    }

    private void actualizarBD(Candidato unCandidato) throws SQLException {

        String condicion = daoCandidato.obtenerCondicionElemento(unCandidato);
        daoCandidato.updateElement(unCandidato, condicion);
    }

    /**
     * Elimina un candidato, de la lista de candidatos.
     *
     * @param id
     */
    public void eliminarCandidato(int id) {
        try {
            //lo eliminamos de la caché:
            //cache.delete(id);
            Candidato candidatoAEliminar = (Candidato) daoCandidato.
                    findElement("candidato", "candidato_id = " + id);
            daoCandidato.deleteElement(candidatoAEliminar);

            llenarCache();
            //notificamos del cambio.
            notificarObservadoresEvento(0);
        } catch (SQLException ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
    }

    @Override
    public Object getDatos() {

        try {
            //es más rápido que vaya a la caché:
            //si solo es para mostrar datos.
            if (!cache.toArray(1, contadorCandidatos).isEmpty()) {
                //entonces la caché está actualizada:
                datos = cache.toArray(1, contadorCandidatos);    
            } else {
                //entonces la caché no tiene la información
                //se la pedimos a la bd.
                datos = daoCandidato.getAllFromTable("candidato");
            }
        } catch (ExcepcionObjetoDesconocido  ex) {
            ex.printStackTrace();
        }
        return datos;
    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

}
