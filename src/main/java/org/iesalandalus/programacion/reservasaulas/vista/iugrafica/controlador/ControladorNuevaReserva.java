package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorNuevaReserva implements Initializable {

	@FXML
	private ComboBox<String> cbProfesor;
	@FXML
	private Label lbProfesorCorreo;
	@FXML
	private Label lbProfesorTelefono;
	@FXML
	private ComboBox<String> cbAula;
	@FXML
	private Label lbAulaPuestos;
	@FXML
	private DatePicker dpDia;
	@FXML
	private RadioButton rbTramo;
	@FXML
	private RadioButton rbHora;
	@FXML
	private Label lbTramoHora;
	@FXML
	private ComboBox<String> cbTramoHora;

	@FXML
	private void aceptarNuevaReserva() {
		try {
			crearPermanencia();
			Reserva reserva = new Reserva(profesor, aula, permanencia);
			controladorMVC.realizarReserva(reserva);
			controladorPrincipal.actualizarReservas();
			escenarioNuevaReserva.close();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}

	@FXML
	private void cancelar() {
		escenarioNuevaReserva.close();
	}

	private ToggleGroup tgRadioBotones;
	private String dia;
	private String tramoHora;
	private Aula aula;
	private Profesor profesor;
	private Permanencia permanencia;
	
	private ControladorEscenaPrincipal controladorPrincipal;
	private IControladorReservasAulas controladorMVC;
	private Stage escenarioNuevaReserva;
	private ObservableList<String> listaTramoHora;
	private ObservableList<Profesor> profesores;
	private ObservableList<Aula> aulas;

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setPrincipal(ControladorEscenaPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}
	
	public void setListas(ObservableList<Profesor> profesores, ObservableList<Aula> aulas) {
		this.profesores = profesores;
		this.aulas = aulas;
		cargarCajasCombinadas();
	}
	
	public void setEscenario(Stage escenarioNuevaReserva) {
		this.escenarioNuevaReserva = escenarioNuevaReserva;
	}
	
	private void seleccionarProfesor(String nombreProfesor) {
		Profesor profesorBuscar = new Profesor(nombreProfesor, "a@a.a");
		profesor = controladorMVC.buscarProfesor(profesorBuscar);
		if (profesor != null) {
			lbProfesorCorreo.setText(profesor.getCorreo());
			lbProfesorTelefono.setText(profesor.getTelefono());
		}
	}

	private void seleccionarAula(String nombreAula) {
		Aula aulaBuscar = new Aula(nombreAula, 10);
		aula = controladorMVC.buscarAula(aulaBuscar);
		if (aula != null) {
			lbAulaPuestos.setText(String.valueOf(aula.getPuestos()));
		}
	}
	
	private void seleccionarDia(String diaSeleccionado) {
		String[] diaSplit = diaSeleccionado.split("/");
		if(diaSplit[0].length() != 2) {
			diaSplit[0] = "0" + diaSplit[0];
		}
		dia = diaSplit[0] + "/" + diaSplit[1] + "/" + diaSplit[2];
	}
	
	private void seleccionarTipo(RadioButton rb) {
		cbTramoHora.setDisable(false);
		if (rb.equals(rbHora)) {
			listaTramoHora = FXCollections.observableArrayList();
			for (int i = 8; i <= 22; i++) {
				if (i > 0 && i < 10) {
					listaTramoHora.add("0" + i + ":00");
				} else {
					listaTramoHora.add(i + ":00");
				}
			}
		}
		if (rb.equals(rbTramo)) {
			listaTramoHora = FXCollections.observableArrayList("Mañana", "Tarde");
		}
		cbTramoHora.setItems(listaTramoHora);
	}

	private void crearPermanencia() {
		RadioButton rb = (RadioButton) tgRadioBotones.getSelectedToggle();
		if (rb != null) {
			if (rb.equals(rbHora)) {
				permanencia = new PermanenciaPorHora(dia, tramoHora);
			}
			if (rb.equals(rbTramo) && tramoHora != null) {
				if (tramoHora.equals("Mañana")) {
					permanencia = new PermanenciaPorTramo(dia, Tramo.MANANA);
				} else {
					permanencia = new PermanenciaPorTramo(dia, Tramo.TARDE);
				}
			}
		}
	}

	private void cargarCajasCombinadas() {
		for(Profesor profesor : profesores) {
			cbProfesor.getItems().add(profesor.getNombre());
		}
		for(Aula aula : aulas) {
			cbAula.getItems().add(aula.getNombre());
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tgRadioBotones = new ToggleGroup();
		tgRadioBotones.getToggles().addAll(rbTramo, rbHora);
		cbTramoHora.setDisable(true);
		dpDia.getEditor().setEditable(false);
		profesor = null;
		aula = null;
		permanencia = null;
		
		cbProfesor.valueProperty().addListener((ob, oldValue, newValue) -> seleccionarProfesor(newValue));
		cbAula.valueProperty().addListener((ob, oldValue, newValue) -> seleccionarAula(newValue));
		dpDia.getEditor().textProperty().addListener((ob, oldValue, newValue) -> seleccionarDia(newValue));
		tgRadioBotones.selectedToggleProperty().addListener((ob, oldValue, newValue) -> seleccionarTipo((RadioButton) newValue));
		cbTramoHora.valueProperty().addListener((ob, oldValue, newValue) -> tramoHora = newValue);
	}
}
