package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.VistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorNuevoProfesor implements Initializable {

	@FXML
	private TextField tfNombre;
	@FXML
	private TextField tfCorreo;
	@FXML
	private TextField tfTelefono;

	@FXML
	private void validarNombre(String nombre) {
		try {
			profesor = new Profesor(nombre, "a@a.a");
			this.nombre = nombre;
			tfNombre.setStyle("-fx-background-color: #a9ffa5");
		} catch (IllegalArgumentException e) {
			tfNombre.setStyle("-fx-background-color: #ffa5a5");
		}
	}

	@FXML
	private void validarCorreo(String correo) {
		try {
			profesor.setCorreo(correo);
			this.correo = correo;
			tfCorreo.setStyle("-fx-background-color: #a9ffa5");
		} catch (IllegalArgumentException e) {
			tfCorreo.setStyle("-fx-background-color: #ffa5a5");
		}
	}

	@FXML
	private void validarTelefono(String telefono) {
		try {
			profesor.setTelefono(telefono);
			this.telefono = telefono;
			tfTelefono.setStyle("-fx-background-color: #a9ffa5");
		} catch (IllegalArgumentException e) {
			if (!telefono.equals("")) {
				tfTelefono.setStyle("-fx-background-color: #ffa5a5");
			} else {
				this.telefono = telefono;
				tfTelefono.setStyle("-fx-background-color: #a9ffa5");
			}
		}
	}

	@FXML
	private void aceptarNuevoProfesor() {
		try {
			if(profesor.getNombre().equals("nn")) {
				profesor = new Profesor("", correo, telefono);
			} else {
				profesor = new Profesor(nombre, correo, telefono);
				controladorMVC.insertarProfesor(profesor);
				controladorPrincipal.actualizarProfesores();
				escenario.close();
			}
		} catch (IllegalArgumentException | OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}

	@FXML
	private void cancelar() {
		escenario.close();
	}

	private IControladorReservasAulas controladorMVC;
	private ControladorEscenaPrincipal controladorPrincipal;
	private Stage escenario;
	private Profesor profesor;
	private String nombre;
	private String correo;
	private String telefono;

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setPrincipal(ControladorEscenaPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}

	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfNombre.textProperty().addListener((ob, oldValue, newValue) -> validarNombre(newValue));
		tfCorreo.textProperty().addListener((ob, oldValue, newValue) -> validarCorreo(newValue));
		tfTelefono.textProperty().addListener((ob, oldValue, newValue) -> validarTelefono(newValue));
		profesor = new Profesor("nn", "a@a.a");
	}
}
