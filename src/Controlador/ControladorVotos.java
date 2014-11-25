/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Fmat.Framework.Controlador.ClaseControlador;
import Fmat.Framework.Modelo.ClaseModelo;
import Modelo.AdminVotos;
import Modelo.Candidato;
import Vista.VentanaPrincipal;
import java.util.ArrayList;

/**
 *
 * @author Lalo
 */
public class ControladorVotos extends ClaseControlador {
    
    ControladorSesion controlSesion = new ControladorSesion(((AdminVotos) super.getModelo()), 0);
    VentanaPrincipal ventanaPrincipal;

    public ControladorVotos(ClaseModelo modelo, int idEvento) {
        super(modelo, idEvento);
        ventanaPrincipal = VentanaPrincipal.getInstance(this, controlSesion);
    }

    public void realizarVotacion(int idCandidato) {
        ((AdminVotos) super.getModelo()).agregarVoto(idCandidato);
    }

    @Override
    public void actualizar(Object obj) {
        actualizarVentana();
    }

    public void actualizarVentana() {
        ventanaPrincipal.iniciarDatosEnVentana((ArrayList<Candidato>) modelo.getDatos());
        ventanaPrincipal.setVisible(true);
    }
    
}
