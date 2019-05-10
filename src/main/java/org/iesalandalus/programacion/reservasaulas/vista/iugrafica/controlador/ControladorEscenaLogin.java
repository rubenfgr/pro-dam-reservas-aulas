package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.VistaReservasAulas;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControladorEscenaLogin {

    @FXML
    void entrar() {
    	vista.cambiarEscena();
    }
    
	private VistaReservasAulas vista;
	
	public void setVista(VistaReservasAulas vista) {
		this.vista = vista;
	}
}
