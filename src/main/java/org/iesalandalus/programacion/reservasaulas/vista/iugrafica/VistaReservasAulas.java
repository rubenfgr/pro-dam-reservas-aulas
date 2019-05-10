package org.iesalandalus.programacion.reservasaulas.vista.iugrafica;

import java.io.IOException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorEscenaLogin;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorEscenaPrincipal;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaReservasAulas extends Application implements IVistaReservasAulas {

	private IControladorReservasAulas controladorMVC = null;
	private static VistaReservasAulas instancia = null;
	private Ventanas ventanas;
	private Stage escenarioPrincipal;
	private Scene escenaLogin;
	private Scene escenaPrincipal;

	public VistaReservasAulas() {
		if (instancia != null) {
			controladorMVC = instancia.controladorMVC;
		} else {
			instancia = this;
		}
		ventanas = new Ventanas();
		ventanas.setControlador(controladorMVC);
	}

	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		this.escenarioPrincipal = escenarioPrincipal;
		escenarioPrincipal.setTitle("Gestión de reservas de aulas");
		escenarioPrincipal.setResizable(false);
		escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));

		iniciarLogin();
		
		iniciarPrincipal();
		
		escenarioPrincipal.show();
	}
	
	public void iniciarLogin() {
		try {
			FXMLLoader cargador = new FXMLLoader(getClass().getResource("vista/EscenaLogin.fxml"));
			AnchorPane login = (AnchorPane) cargador.load();
			ControladorEscenaLogin controladorEscenaLogin = cargador.getController();
			controladorEscenaLogin.setVista(this);
			
			escenaLogin = new Scene(login);
			escenarioPrincipal.setScene(escenaLogin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void iniciarPrincipal() {
		try {
			FXMLLoader cargador = new FXMLLoader(getClass().getResource("vista/EscenaPrincipal.fxml"));
			AnchorPane principal = (AnchorPane) cargador.load();
			ControladorEscenaPrincipal controladorEscenaPrincipal = cargador.getController();
			controladorEscenaPrincipal.setControlador(controladorMVC);
			controladorEscenaPrincipal.setVista(this);
			controladorEscenaPrincipal.setEscenarioPrincipal(escenarioPrincipal);
			controladorEscenaPrincipal.setVentanas(ventanas);
			controladorEscenaPrincipal.iniciar();
			
			ventanas.setPrincipal(controladorEscenaPrincipal);
			ventanas.setEscenarioPrincipal(escenarioPrincipal);
			
			escenaPrincipal = new Scene(principal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cambiarEscena() {
		if(escenarioPrincipal.getScene().equals(escenaPrincipal)) {
			escenarioPrincipal.setScene(escenaLogin);
		} else {
			escenarioPrincipal.setScene(escenaPrincipal);
		}
	}
	
	public void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			controladorMVC.salir();
			escenarioPrincipal.close();
		}
		else
			e.consume();	
	}

	@Override
	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
	}

}
