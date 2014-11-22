/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Fmat.Framework.Modelo.ClaseEvento;
import Fmat.Framework.Modelo.ClaseModelo;
import java.util.ArrayList;

/**
 *
 * @author Lalo
 */
public class AdminVotos extends ClaseModelo {

    private static AdminVotos adminVtos;
    private ArrayList<Candidato> listaCandidatos;

    private AdminVotos() {

        this.listaCandidatos = new ArrayList();
        inicializarCandidatos();
        inicializarEventos();

        //seteamos el arreglo de la clase Padre. (hereda del framework) 
        super.datos = listaCandidatos;

    }

    /**
     * Devuelve la instancia de esta clase, con la intención de tener solo una
     * instancia.
     *
     * @return
     */
    public static AdminVotos getInstance() {
        if (adminVtos == null) {
            adminVtos = new AdminVotos();
        }
        return adminVtos;
    }

    /**
     * Inicializa en memoria 3 candidatos por default.
     */
    private void inicializarCandidatos() {

        listaCandidatos.add(new Candidato(1, "Pepe", 0));

        listaCandidatos.add(new Candidato(2, "Esteban", 0));

        listaCandidatos.add(new Candidato(3, "Jorge", 0));

    }

    private void inicializarEventos() {
        for (int i = 0; i < 3; i++) {
            eventos.add(new ClaseEvento(i));
        }
    }

    public void agregarVoto(int idObjeto) {

        for (Candidato unCandidato : listaCandidatos) {
            if (unCandidato.getIdCandidato() == idObjeto) {
                unCandidato.agregarVoto();
            }
        }
        notificarObservadoresEvento(0);
    }

    /**
     * Agrega un candidato, a la lista de candidatos.
     *
     * @param id
     * @param nombre
     */
    public void agregarCandidatos(int id, String nombre) {
        Candidato temp = new Candidato(id, nombre, 0);

        listaCandidatos.add(temp);

        notificarObservadoresEvento(0);
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

    @Override
    public Object getDatos() {
        return super.datos;
    }

}
