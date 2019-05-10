package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.Ventanas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ControladorProfesores implements Initializable {

	@FXML
	private TextField tfBuscarProfesor;

	@FXML
	private TableView<Profesor> tvProfesores;

	@FXML
	private TableColumn<Profesor, String> tcNombreProfesor;

	@FXML
	private TableColumn<Profesor, String> tcCorreoProfesor;

	@FXML
	private TableColumn<Profesor, String> tcTelefonoProfesor;

	@FXML
	private TextField tfBuscarReserva;

	@FXML
	private TableView<Reserva> tvReservasProfesor;

	@FXML
	private TableColumn<Reserva, String> tcReservaDia;

	@FXML
	private TableColumn<Reserva, String> tcReservaTramoHora;

	@FXML
	private TableColumn<Reserva, String> tcReservaAula;

	@FXML
	private TableColumn<Reserva, String> tcReservaTipo;

	@FXML
	void nuevoProfesor() {
		ventanas.agregarProfesor();
	}

	@FXML
	void borrarProfesor() {
		ventanas.borrarProfesor(tvProfesores.getSelectionModel().getSelectedItem());
	}

	@FXML
	void nuevaReserva() {
		ventanas.nuevaReserva();
		reservasProfesor(tvProfesores.getSelectionModel().getSelectedItem());
	}

	@FXML
	void anularReservaProfesor() {
		ventanas.anularReserva(tvReservasProfesor.getSelectionModel().getSelectedItem());
		reservasProfesor(tvProfesores.getSelectionModel().getSelectedItem());
	}

	private IControladorReservasAulas controladorMVC;
	private ControladorEscenaPrincipal controladorPrincipal;
	private Ventanas ventanas;
	private ObservableList<Reserva> reservasProfesor;
	private FilteredList<Reserva> reservasFiltradas;

	public void setPrincipal(ControladorEscenaPrincipal controladorPrincipal) {
		this.controladorPrincipal = controladorPrincipal;
	}

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	public void setVentanas(Ventanas ventanas) {
		this.ventanas = ventanas;
	}
	
	public void iniciar(ObservableList<Profesor> profesores) {
		tvProfesores.setItems(profesores);

		filtrarProfesores(profesores);
	}

	private String obtenerTipo(Reserva reserva) {
		Permanencia permanencia = reserva.getPermanencia();
		if (permanencia instanceof PermanenciaPorHora) {
			return "Por hora";
		} else {
			return "Por tramo";
		}
	}
	
	private void filtrarProfesores(ObservableList<Profesor> profesores) {
		FilteredList<Profesor> profesoresFiltrados = new FilteredList<>(profesores, p -> true);

		tfBuscarProfesor.textProperty().addListener((ov, oldValue, newValue) -> {
			profesoresFiltrados.setPredicate(profesor -> {
				boolean encontrado = false;
				if (newValue == null || newValue.isEmpty())
					return true;

				try {
					String name = profesor.getNombre().toLowerCase();
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
					name = profesor.getCorreo().toLowerCase();
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
					name = profesor.getTelefono().toLowerCase();
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
				} catch (NullPointerException e) {
					// O_x
				}
				return encontrado;
			});
		});

		SortedList<Profesor> profesoresOrdenados = new SortedList<>(profesoresFiltrados);
		profesoresOrdenados.comparatorProperty().bind(tvProfesores.comparatorProperty());

		tvProfesores.setItems(profesoresOrdenados);
	}
	
	private void filtrarReservasProfesor() {
		if (reservasProfesor != null) {
			reservasFiltradas = new FilteredList<>(reservasProfesor, p -> true);

			tfBuscarReserva.textProperty().addListener((ov, oldValue, newValue) -> {
				reservasFiltradas.setPredicate(reserva -> {
					boolean encontrado = false;
					if (newValue == null || newValue.isEmpty())
						return true;

					try {
						String name = reserva.getAula().getNombre().toLowerCase();
						if (name.contains(newValue.toLowerCase()))
							encontrado = true;
						name = reserva.getPermanencia().getDia().toString();
						if (name.contains(newValue.toLowerCase()))
							encontrado = true;
						name = obtenerTipo(reserva);
						if (name.contains(newValue.toLowerCase()))
							encontrado = true;
						name = obtenerHoraTramo(reserva);
						if (name.contains(newValue.toLowerCase()))
							encontrado = true;
					} catch (NullPointerException e) {
						// O_x
					}
					return encontrado;

				});
			});

			SortedList<Reserva> reservasOrdenadas = new SortedList<>(reservasFiltradas);
			reservasOrdenadas.comparatorProperty().bind(tvReservasProfesor.comparatorProperty());

			tvReservasProfesor.setItems(reservasOrdenadas);
		}
	}
	
	private void reservasProfesor(Profesor profesor) {
		if (profesor == null) {
			tvReservasProfesor.setPlaceholder(new Label("No existen reservas para este profesor"));
		} else {
			reservasProfesor = FXCollections.observableArrayList(controladorMVC.getReservasProfesor(profesor));
			filtrarReservasProfesor();
		}
	}

	private String obtenerHoraTramo(Reserva reserva) {
		Permanencia permanencia = reserva.getPermanencia();
		if (permanencia instanceof PermanenciaPorHora) {
			return ((PermanenciaPorHora) permanencia).getHora().toString();
		} else {
			return ((PermanenciaPorTramo) permanencia).getTramo().toString();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcNombreProfesor.setCellValueFactory(new PropertyValueFactory<Profesor, String>("nombre"));
		tcCorreoProfesor.setCellValueFactory(new PropertyValueFactory<Profesor, String>("correo"));
		tcTelefonoProfesor.setCellValueFactory(new PropertyValueFactory<Profesor, String>("telefono"));

		tcReservaAula.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcReservaDia.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().getDia().toString()));
		tcReservaTipo.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerTipo(reserva.getValue())));
		tcReservaTramoHora.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerHoraTramo(reserva.getValue())));
		
		reservasProfesor(null);

		tvProfesores.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> reservasProfesor(newValue));
	}
}
