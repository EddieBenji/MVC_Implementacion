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

/**
 *
 * @author Lalo
 */
public class AdminCandidato extends ClaseModelo {

    private static AdminCandidato adminVtos;
    private final ControladorCache cache;
    private DAOCandidato daoCandidato ;

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
        agregarCandidatos(1, "Pepe");
        agregarCandidatos(2, "Esteban");
        agregarCandidatos(3, "Jorge");
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
            //lo mete a la caché:
            cache.put(nuevoCandidato);
            //lo mete a la BD:
            daoCandidato.addElement(nuevoCandidato);

            notificarObservadoresEvento(0);

        } catch (ExcepcionObjetoDuplicado | SQLException ex) {
            System.out.println("Error: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void agregarVoto(int idCandidato) {
        try {
            Candidato unCandidato = (Candidato) cache.get(idCandidato);
            unCandidato.agregarVoto();

            
            String condicion = daoCandidato.obtenerCondicionElemento(unCandidato);
            daoCandidato.updateElement(unCandidato, condicion);
            //***************
            
            cache.put(unCandidato);
            notificarObservadoresEvento(0);
        } catch (ExcepcionObjetoDesconocido | ExcepcionObjetoDuplicado | SQLException ex) {
            System.out.println("Error:");
            ex.printStackTrace();
       }
    }
    
        

    /**
     * Elimina un candidato, de la lista de candidatos.
     *
     * @param id
     */
    public void eliminarCandidatos(int id) {
        try {
            //lo eliminamos de la caché:
            cache.delete(id);
            
            Candidato candidatoAEliminar= (Candidato)daoCandidato.
                    findElement("mvcdb.candidato", "usuario_id = " + id);
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

    @Override
    public Object getDatos() {
        try {
            datos = cache.toArray(1, contadorCandidatos);
        } catch (ExcepcionObjetoDesconocido ex) {
            System.out.println("Error en el llenado");
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
