/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Clases.ControladorCache;
import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.awt.Window;
import java.util.ArrayList;
import org.apache.jcs.access.exception.CacheException;

/**
 *
 * @author Lalo
 */
public class AdminCandidato extends ClaseModelo {

    private static AdminCandidato adminVtos;
    private final ControladorCache cache;
    private static final int MAX_ELEMENTOS_CACHE = 1000;
    

   // public ArrayList observadores;

    private AdminCandidato() {
      //  observadores = new ArrayList();
        cache = ControladorCache.getInstanciaCache();
        try {

            cache.configLoad();
        } catch (CacheException ex) {
            System.out.println("Error con la inicialización de la caché");
            ex.printStackTrace();
        }
        inicializarCandidatos();
        inicializarEventos();
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
        try {
            Candidato A = new Candidato(1, "Pepe", 0);
            cache.put(A.getID(), A);

            Candidato B = new Candidato(2, "Esteban", 0);
            cache.put(B.getID(), B);

            Candidato C = new Candidato(3, "Jorge", 0);
            cache.put(C.getID(), C);
        } catch (CacheException ex) {
            System.out.println("Error con la caché");
            ex.printStackTrace();
        }
    }

    private void inicializarEventos() {
        for (int i = 0; i < 3; i++) {
            eventos.add(new ClaseEvento(i));
        }
    }

    public void agregarVoto(int idCandidato) {
        try {
            Candidato unCandidato = (Candidato) cache.get(idCandidato);
            unCandidato.agregarVoto();
            cache.put(idCandidato, unCandidato);

            notificarObservadoresEvento(0);
        } catch (CacheException ex) {
            System.out.println("Error con la caché");
            ex.printStackTrace();
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
            Candidato nuevoCandidato = new Candidato(id, nombre, 0);

            cache.put(id, nuevoCandidato);
            notificarObservadoresEvento(0);
        } catch (CacheException ex) {

            System.out.println("Error con la caché");
            ex.printStackTrace();
        }
    }

    /**
     * Elimina un candidato, de la lista de candidatos.
     *
     * @param nombre
     * @param peticion
     */
    public void eliminarCandidatos(String nombre, String peticion) {
        for (Candidato candidato : ((ArrayList<Candidato>) getDatos())) {
            if (candidato.getNombre().equals(nombre)) {
                ((ArrayList<Candidato>) getDatos()).remove(candidato);
            }
        }
        notificarObservadoresEvento(0);
    }

    private ArrayList<Candidato> obtenerCandidatos() {
        //declaramos el ArrayList que contendrá la información de los candidatos:
        ArrayList<Candidato> candidatos = new ArrayList<>();

        //recorremos toda la caché:
        for (int i = 1; i <= MAX_ELEMENTOS_CACHE; i++) {

            try {
                //obtenemos el candidato de la caché:
                Candidato unCandidato = (Candidato) cache.get(i);

                //si lo que devuelve la caché es nulo, entonces dejamos de recorrer
                //toda la caché.
                if (unCandidato == null) {
                    break;
                }

                candidatos.add(unCandidato);
            } catch (CacheException ex) {
                System.out.println("Error con la caché");
                ex.printStackTrace();
            }

        }
        /*Nótese que este ArrayList, fue llenado 
         con la información de la caché.*/
        return candidatos;
    }

    @Override
    public Object getDatos() {
        datos = obtenerCandidatos();
        return datos;
    }

    public void cerrarVentanas() {
        for (Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }

}
