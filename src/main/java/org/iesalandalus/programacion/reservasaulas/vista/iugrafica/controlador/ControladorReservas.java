package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.Ventanas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorReservas implements Initializable {

	@FXML
	private TableView<Reserva> tvReservas;

	@FXML
	private TableColumn<Reserva, String> tcProfesor;

	@FXML
	private TableColumn<Reserva, String> tcAula;

	@FXML
	private TableColumn<Reserva, String> tcDia;

	@FXML
	private TableColumn<Reserva, String> tcTipo;

	@FXML
	private TableColumn<Reserva, String> tcTramoHora;

	@FXML
	private Label lbReservaTramoHora;

	@FXML
	private Label lbAulaNombre;

	@FXML
	private Label lbProfesorCorreo;

	@FXML
	private TextField tfBuscar;

	@FXML
	private Label lbReservaTipo;

	@FXML
	private Label lbReservaDia;

	@FXML
	private Label lbProfesorNombre;

	@FXML
	private Label lbProfesorTelefono;

	@FXML
	private Label lbAulaPuestos;

	@FXML
	void nuevaReserva() {
		ventanas.nuevaReserva();
	}

	@FXML
	void anularReserva() {
		ventanas.anularReserva(tvReservas.getSelectionModel().getSelectedItem());
	}

	private IControladorReservasAulas controladorMVC;
	private ControladorEscenaPrincipal controladorPrincipal;
	private Ventanas ventanas;
	private Stage escenarioPrincipal;
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

	public void setEscenarioPrincipal(Stage escenarioPrincipal) {
		this.escenarioPrincipal = escenarioPrincipal;
	}

	public void iniciarReservas(ObservableList<Reserva> reservas) {
		tvReservas.setItems(reservas);

		filtrarReservas(reservas);
	}

	private void filtrarReservas(ObservableList<Reserva> reservas) {
		reservasFiltradas = new FilteredList<>(reservas, p -> true);

		tfBuscar.textProperty().addListener((ov, oldValue, newValue) -> {
			reservasFiltradas.setPredicate(reserva -> {
				boolean encontrado = false;
				if (newValue == null || newValue.isEmpty())
					return true;

				try {
					String name = reserva.getProfesor().getNombre().toLowerCase();
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
					name = reserva.getAula().getNombre().toLowerCase();
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
		reservasOrdenadas.comparatorProperty().bind(tvReservas.comparatorProperty());

		tvReservas.setItems(reservasOrdenadas);
	}

	private String obtenerTipo(Reserva reserva) {
		Permanencia permanencia = reserva.getPermanencia();
		if (permanencia instanceof PermanenciaPorHora) {
			return "Por hora";
		} else {
			return "Por tramo";
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

	private void detallesReserva(Reserva reserva) {
		if (reserva != null) {
			lbReservaDia.setText(reserva.getPermanencia().getDia().toString());
			lbReservaTipo.setText(obtenerTipo(reserva));
			lbReservaTramoHora.setText(obtenerHoraTramo(reserva));
			lbProfesorNombre.setText(reserva.getProfesor().getNombre());
			lbProfesorCorreo.setText(reserva.getProfesor().getCorreo());
			lbProfesorTelefono.setText(reserva.getProfesor().getTelefono());
			lbAulaNombre.setText(reserva.getAula().getNombre());
			lbAulaPuestos.setText(String.valueOf(reserva.getAula().getPuestos()));
		} else {
			lbReservaDia.setText("---");
			lbReservaTipo.setText("---");
			lbReservaTramoHora.setText("---");
			lbProfesorNombre.setText("---");
			lbProfesorCorreo.setText("---");
			lbProfesorTelefono.setText("---");
			lbAulaNombre.setText("---");
			lbAulaPuestos.setText("---");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tcProfesor
				.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcAula.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getAula().getNombre()));
		tcDia.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().getDia().toString()));
		tcTipo.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerTipo(reserva.getValue())));
		tcTramoHora.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerHoraTramo(reserva.getValue())));

		detallesReserva(null);

		tvReservas.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> detallesReserva(newValue));
	}
}
