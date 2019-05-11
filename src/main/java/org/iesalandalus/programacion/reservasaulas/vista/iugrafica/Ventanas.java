package org.iesalandalus.programacion.reservasaulas.vista.iugrafica;

import java.io.IOException;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorEscenaPrincipal;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorNuevaReserva;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorNuevoAula;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador.ControladorNuevoProfesor;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Ventanas {

	private IControladorReservasAulas controladorMVC;
	private ControladorEscenaPrincipal controladorPrincipal;
	private ObservableList<Profesor> profesores;
	private ObservableList<Aula> aulas;
	private Stage escenarioPrincipal;
	private Stage escenarioNuevaReserva;
	private Stage escenarioAgregarProfesor;
	private Stage escenarioAgregarAula;
	private Image logo = new Image(getClass().getResourceAsStream("imagenes/logo.png"), 20, 20, true, true);

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setPrincipal(ControladorEscenaPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}

	public void setEscenarioPrincipal(Stage escenarioPrincipal) {
		this.escenarioPrincipal = escenarioPrincipal;
	}

	public void nuevaReserva() {
		crearNuevaReserva();
		escenarioNuevaReserva.showAndWait();
	}

	public void agregarProfesor() {
		crearAgregarProfesor();
		escenarioAgregarProfesor.showAndWait();
	}
	
	public void agregarAula() {
		crearAgregarAula();
		escenarioAgregarAula.showAndWait();
	}

	public void setListas(ObservableList<Profesor> profesores,
			ObservableList<Aula> aulas) {
		this.profesores = profesores;
		this.aulas = aulas;
	}

	public void anularReserva(Reserva reserva) {
		try {
			if (reserva != null) {
				if (Dialogos.mostrarDialogoConfirmacion("Confirmación", "¿Seguro que quiere anular la reserva?",
						escenarioPrincipal)) {
					controladorMVC.anularReserva(reserva);
					controladorPrincipal.actualizarReservas();
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Advertencia", "Debe seleccionar una reserva");
			}
		} catch (OperationNotSupportedException e) {
			Dialogos.mostrarDialogoError("ERROR", e.getMessage());
		}
	}

	public void borrarProfesor(Profesor profesor) {
		if (profesor == null) {
			Dialogos.mostrarDialogoInformacion("Información", "Debe seleccionar un profesor");
		} else {
			if (controladorMVC.getReservasProfesor(profesor).size() < 1) {
				if (Dialogos.mostrarDialogoConfirmacion("Confirmación", "¿Está seguro que quiere eliminar el profesor?",
						escenarioPrincipal)) {
					try {
						controladorMVC.borrarProfesor(profesor);
						controladorPrincipal.actualizarProfesores();
					} catch (OperationNotSupportedException e) {
						Dialogos.mostrarDialogoError("ERROR", e.getMessage());
					}
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Advertencia",
						"No puede borrar un profesor que tiene reservas realizadas");
			}
		}
	}
	
	public void borrarAula(Aula aula) {
		if (aula == null) {
			Dialogos.mostrarDialogoInformacion("Información", "Debe seleccionar un aula");
		} else {
			if (controladorMVC.getReservasAula(aula).size() < 1) {
				if (Dialogos.mostrarDialogoConfirmacion("Confirmación", "¿Está seguro que quiere eliminar el aula?",
						escenarioPrincipal)) {
					try {
						controladorMVC.borrarAula(aula);
						controladorPrincipal.actualizarAulas();
					} catch (OperationNotSupportedException e) {
						Dialogos.mostrarDialogoError("ERROR", e.getMessage());
					}
				}
			} else {
				Dialogos.mostrarDialogoAdvertencia("Advertencia",
						"No puede borrar un aula que tiene reservas realizadas");
			}
		}
	}

	private void crearNuevaReserva() {
		try {
			if (escenarioNuevaReserva == null) {
				escenarioNuevaReserva = new Stage();
				FXMLLoader cargador = new FXMLLoader(getClass().getResource("vista/NuevaReserva.fxml"));
				AnchorPane panelNuevaReserva = (AnchorPane) cargador.load();
				Scene escenaNuevaReserva = new Scene(panelNuevaReserva);

				ControladorNuevaReserva controladorNuevaReserva = cargador.getController();
				controladorNuevaReserva.setPrincipal(controladorPrincipal);
				controladorNuevaReserva.setControlador(controladorMVC);
				controladorNuevaReserva.setListas(profesores, aulas);
				controladorNuevaReserva.setEscenario(escenarioNuevaReserva);
				escenarioNuevaReserva.setTitle("Nueva reserva");
				escenarioNuevaReserva.initModality(Modality.WINDOW_MODAL);
				escenarioNuevaReserva.initOwner(escenarioPrincipal);
				escenarioNuevaReserva.setScene(escenaNuevaReserva);
				escenarioNuevaReserva.setResizable(false);
				escenarioNuevaReserva.getIcons().add(logo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void crearAgregarProfesor() {
		try {
			if (escenarioAgregarProfesor == null) {
				escenarioAgregarProfesor = new Stage();
				FXMLLoader cargador = new FXMLLoader(getClass().getResource("vista/NuevoProfesor.fxml"));
				AnchorPane panelAgregarProfesor = (AnchorPane) cargador.load();
				Scene escenaAgregarProfesor = new Scene(panelAgregarProfesor);

				ControladorNuevoProfesor controladorNuevoProfesor = cargador.getController();
				controladorNuevoProfesor.setPrincipal(controladorPrincipal);
				controladorNuevoProfesor.setControlador(controladorMVC);
				controladorNuevoProfesor.setEscenario(escenarioAgregarProfesor);
				escenarioAgregarProfesor.setTitle("Agregar profesor");
				escenarioAgregarProfesor.initModality(Modality.WINDOW_MODAL);
				escenarioAgregarProfesor.initOwner(escenarioPrincipal);
				escenarioAgregarProfesor.setScene(escenaAgregarProfesor);
				escenarioAgregarProfesor.setResizable(false);
				escenarioAgregarProfesor.getIcons().add(logo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void crearAgregarAula() {
		try {
			if (escenarioAgregarAula == null) {
				escenarioAgregarAula = new Stage();
				FXMLLoader cargador = new FXMLLoader(getClass().getResource("vista/NuevoAula.fxml"));
				AnchorPane panelAgregarAula = (AnchorPane) cargador.load();
				Scene escenaAgregarAula = new Scene(panelAgregarAula);

				ControladorNuevoAula controladorNuevoAula = cargador.getController();
				controladorNuevoAula.setPrincipal(controladorPrincipal);
				controladorNuevoAula.setControlador(controladorMVC);
				controladorNuevoAula.setEscenario(escenarioAgregarAula);
				escenarioAgregarAula.setTitle("Agregar aula");
				escenarioAgregarAula.initModality(Modality.WINDOW_MODAL);
				escenarioAgregarAula.initOwner(escenarioPrincipal);
				escenarioAgregarAula.setScene(escenaAgregarAula);
				escenarioAgregarAula.setResizable(false);
				escenarioAgregarAula.getIcons().add(logo);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
