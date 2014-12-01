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

    //private int contadorCandidatos = 0;

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
        /*
         agregarCandidatos(1, "Pepe");
         agregarCandidatos(2, "Esteban");
         agregarCandidatos(3, "Jorge");
         */
        llenarCache();
    }

    public void llenarCache() {
        try {
            //contadorCandidatos = 0;
            cache.limpiarCache();

            for (Object unCandidato : daoCandidato.getAllFromTable("candidato")) {
                try {
                    cache.put((Candidato) unCandidato);
              //      contadorCandidatos++;
                } catch (ExcepcionObjetoDuplicado ex) {
                    Logger.getLogger(AdminCandidato.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (CacheException ex) {
            Logger.getLogger(AdminCandidato.class.getName()).log(Level.SEVERE, null, ex);
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

            //contadorCandidatos++;
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
                Candidato unCandidato = (Candidato) daoCandidato.
                        findElement("candidato", "candidato_id = " + idCandidato);
                unCandidato.agregarVoto();

                actualizarBD(unCandidato);
                cache.put(unCandidato);
                
            } catch (SQLException | ExcepcionObjetoDuplicado ex1) {

            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCandidato.class.getName()).log(Level.SEVERE, null, ex);
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
    /*
    public void eliminarCandidatos(int id) {
        try {
            //lo eliminamos de la caché:
            cache.delete(id);

            Candidato candidatoAEliminar = (Candidato) daoCandidato.
                    findElement("candidato", "usuario_id = " + id);
            daoCandidato.deleteElement(candidatoAEliminar);

            if (id != contadorCandidatos) {//entonces no eliminará el último.
                //Obtenemos el último de la caché:
                Candidato unCandidato = (Candidato) cache.get(contadorCandidatos);
                String condicion = daoCandidato.obtenerCondicionElemento(unCandidato);

                //a ese último, le seteamos el id del que acabamos de eliminar:
                unCandidato.setIdCandidato(id);

                //metemos el último con el id del que eliminamos.
                cache.put(unCandidato);
                daoCandidato.updateElement(unCandidato, condicion);

                //boramos el último, para no tener duplicados
                cache.delete(contadorCandidatos);
            }

            //actualizamos el contador, ya que se 
            //disminuyó en uno la cantidad de datos en la memoria.
            contadorCandidatos--;

            //notificamos del cambio.
            notificarObservadoresEvento(0);
        } catch (ExcepcionObjetoDesconocido | ExcepcionObjetoDuplicado | SQLException ex) {
            System.out.println("Error:");
            ex.printStackTrace();
        }
    }
*/
    @Override
    public Object getDatos() {
        datos = daoCandidato.getAllFromTable("candidato");
        return datos;
    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

}
