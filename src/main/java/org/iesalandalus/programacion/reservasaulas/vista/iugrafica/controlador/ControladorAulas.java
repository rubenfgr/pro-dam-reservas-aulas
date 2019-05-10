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
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.Ventanas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorAulas implements Initializable {

    @FXML
    private TextField tfBuscarAula;

    @FXML
    private TableView<Aula> tvAulas;
    
    @FXML
    private TableColumn<Aula, String> tcNombreAula;

    @FXML
    private TableColumn<Aula, String> tcPuestosAula;

    @FXML
    private TextField tfBuscarReserva;

    @FXML
    private TableView<Reserva> tvReservasAula;

    @FXML
    private TableColumn<Reserva, String> tcReservaProfesor;

    @FXML
    private TableColumn<Reserva, String> tcReservaDia;

    @FXML
    private TableColumn<Reserva, String> tcReservaTramoHora;

    @FXML
    private TableColumn<Reserva, String> tcReservaTipo;

    @FXML
    void nuevoAula() {
    	ventanas.agregarAula();
    }

    @FXML
    void borrarAula() {
    	ventanas.borrarAula(tvAulas.getSelectionModel().getSelectedItem());
    }

    @FXML
    void nuevaReservaAula() {
    	ventanas.nuevaReserva();
    	reservasAulas(tvAulas.getSelectionModel().getSelectedItem());
    }

    @FXML
    void borrarReservaAula() {
    	ventanas.anularReserva(tvReservasAula.getSelectionModel().getSelectedItem());
    	reservasAulas(tvAulas.getSelectionModel().getSelectedItem());
    }
    
    private IControladorReservasAulas controladorMVC;
	private ControladorEscenaPrincipal controladorPrincipal;
	private Ventanas ventanas;
	private ObservableList<Reserva> reservasAula;
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
	
	public void iniciar(ObservableList<Aula> aulas) {
		tvAulas.setItems(aulas);

		filtrarAulas(aulas);
	}

	private String obtenerTipo(Reserva reserva) {
		Permanencia permanencia = reserva.getPermanencia();
		if (permanencia instanceof PermanenciaPorHora) {
			return "Por hora";
		} else {
			return "Por tramo";
		}
	}
	
	private void filtrarAulas(ObservableList<Aula> aulas) {
		FilteredList<Aula> aulasFiltradas = new FilteredList<>(aulas, p -> true);

		tfBuscarAula.textProperty().addListener((ov, oldValue, newValue) -> {
			aulasFiltradas.setPredicate(aula -> {
				boolean encontrado = false;
				if (newValue == null || newValue.isEmpty())
					return true;

				try {
					String name = aula.getNombre().toLowerCase();
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
					name = String.valueOf(aula.getPuestos());
					if (name.contains(newValue.toLowerCase()))
						encontrado = true;
				} catch (NullPointerException e) {
					// O_x
				}
				return encontrado;
			});
		});

		SortedList<Aula> aulasOrdenadas = new SortedList<>(aulasFiltradas);
		aulasOrdenadas.comparatorProperty().bind(tvAulas.comparatorProperty());

		tvAulas.setItems(aulasOrdenadas);
	}
	
	private void filtrarReservasAula() {
		if (reservasAula != null) {
			reservasFiltradas = new FilteredList<>(reservasAula, p -> true);

			tfBuscarReserva.textProperty().addListener((ov, oldValue, newValue) -> {
				reservasFiltradas.setPredicate(reserva -> {
					boolean encontrado = false;
					if (newValue == null || newValue.isEmpty())
						return true;

					try {
						String name = reserva.getProfesor().getNombre().toLowerCase();
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
			reservasOrdenadas.comparatorProperty().bind(tvReservasAula.comparatorProperty());

			tvReservasAula.setItems(reservasOrdenadas);
		}
	}
	
	private void reservasAulas(Aula aula) {
		if (aula == null) {
			tvReservasAula.setPlaceholder(new Label("No existen reservas para este aula"));
		} else {
			reservasAula = FXCollections.observableArrayList(controladorMVC.getReservasAula(aula));
			filtrarReservasAula();
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
		tcNombreAula.setCellValueFactory(new PropertyValueFactory<Aula, String>("nombre"));
		tcPuestosAula.setCellValueFactory(new PropertyValueFactory<Aula, String>("puestos"));

		tcReservaProfesor.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getProfesor().getNombre()));
		tcReservaDia.setCellValueFactory(
				reserva -> new SimpleStringProperty(reserva.getValue().getPermanencia().getDia().toString()));
		tcReservaTipo.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerTipo(reserva.getValue())));
		tcReservaTramoHora.setCellValueFactory(reserva -> new SimpleStringProperty(obtenerHoraTramo(reserva.getValue())));
		
		reservasAulas(null);

		tvAulas.getSelectionModel().selectedItemProperty()
				.addListener((ov, oldValue, newValue) -> reservasAulas(newValue));
	}
}
