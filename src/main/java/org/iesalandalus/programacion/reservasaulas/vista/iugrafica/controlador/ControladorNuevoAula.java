package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorNuevoAula implements Initializable {

	@FXML
	private TextField tfNombre;
	@FXML
	private ComboBox<Integer> cbPuestos;

	@FXML
	private void aceptarNuevoAula() {
		try {
			Integer puestos = cbPuestos.getValue();
			if(puestos == null) {
				puestos = 0;
			}
			aula = new Aula(nombre, puestos);
			controladorMVC.insertarAula(aula);
			controladorPrincipal.actualizarAulas();
			escenario.close();
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
	private Aula aula;
	private String nombre;

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setPrincipal(ControladorEscenaPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}
	
	public void setEscenario(Stage escenario) {
		this.escenario = escenario;
	}
	
	private void validarNombre(String nombre) {
		try {
			aula = new Aula(nombre, 10);
			this.nombre = nombre;
			tfNombre.setStyle("-fx-background-color: #a9ffa5");
		} catch (IllegalArgumentException e) {
			tfNombre.setStyle("-fx-background-color: #ffa5a5");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<Integer> puestos = FXCollections.observableArrayList();
		for (int i = 10; i <= 100; i++) {
			puestos.add(i);
		}
		cbPuestos.setItems(puestos);
		tfNombre.textProperty().addListener((ov, oldValue, newValue) -> validarNombre(newValue));
	}
}
