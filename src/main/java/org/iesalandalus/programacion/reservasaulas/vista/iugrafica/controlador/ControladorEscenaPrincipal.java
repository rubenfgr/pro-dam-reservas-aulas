package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.Ventanas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.VistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControladorEscenaPrincipal implements Initializable {

    @FXML
    private MenuItem miAulas;

    @FXML
    private MenuItem miReservas;

    @FXML
    private MenuItem miProfesores;

    @FXML
    private TabPane tabPane;

    @FXML
    void guardar() {
		try {
			controladorMVC.guardar();
			Dialogos.mostrarDialogoInformacion("Información", "Se guardó con éxito");
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("ERROR", "Salto una excepción: \n" + e.getMessage());
		}
    }

    @FXML
    void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			controladorMVC.salir();
			escenarioPrincipal.close();
		}
    }

    @FXML
    void verReservas() {
		if (tabPane.getTabs().contains(tabReservas)) {
			tabPane.getTabs().remove(tabReservas);
			miReservas.setGraphic(null);
		} else {
			tabPane.getTabs().add(tabReservas);
			miReservas.setGraphic(new ImageView(imgOk));
		}
    }

    @FXML
    void verProfesores() {
		if (tabPane.getTabs().contains(tabProfesores)) {
			tabPane.getTabs().remove(tabProfesores);
			miProfesores.setGraphic(null);
		} else {
			tabPane.getTabs().add(tabProfesores);
			miProfesores.setGraphic(new ImageView(imgOk));
		}
    }

    @FXML
    void verAulas() {
		if (tabPane.getTabs().contains(tabAulas)) {
			tabPane.getTabs().remove(tabAulas);
			miAulas.setGraphic(null);
		} else {
			tabPane.getTabs().add(tabAulas);
			miAulas.setGraphic(new ImageView(imgOk));
		}
    }

    @FXML
    void verPortada() {
    	vista.cambiarEscena();
    }
    
    @FXML
    void verAcercaDe() {
    	Dialogos.acercaDe();
    }
    
    @FXML
    void nuevaReserva() {
    	ventanas.nuevaReserva();
    }
    
    @FXML
    void agregarProfesor() {
    	ventanas.agregarProfesor();
    }
    
    @FXML
    void agregarAula() {
    	ventanas.agregarAula();
    }
	
	private IControladorReservasAulas controladorMVC;
	private VistaReservasAulas vista;
	private Stage escenarioPrincipal;
	private Ventanas ventanas;
    private Tab tabReservas;
    private Tab tabAulas;
    private Tab tabProfesores;
	private Image imgOk = new Image(getClass().getResourceAsStream("../imagenes/ok.png"), 15, 15, true, true);
	private Image logo = new Image(getClass().getResourceAsStream("../imagenes/logo.png"), 20, 20, true, true);
	private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setVista(VistaReservasAulas vista) {
		this.vista = vista;
	}	

	public void setEscenarioPrincipal(Stage escenarioPrincipal) {
		this.escenarioPrincipal = escenarioPrincipal;
		escenarioPrincipal.getIcons().add(logo);
	}
	
	public void setVentanas(Ventanas ventanas) {
		this.ventanas = ventanas;
	}
	
	public void iniciar() {
		iniciarReservas();
		iniciarProfesores();
		iniciarAulas();
		actualizarReservas();
		actualizarProfesores();
		actualizarAulas();
		ventanas.setListas(profesores, aulas);
	}
	
	public void iniciarReservas() {
		try {
			FXMLLoader cargador = new FXMLLoader(getClass().getResource("../vista/Reservas.fxml"));
			AnchorPane vistaReservas = (AnchorPane) cargador.load();
			tabReservas.setContent(vistaReservas);

			ControladorReservas controladorReservas = cargador.getController();
			controladorReservas.setControlador(controladorMVC);
			controladorReservas.setPrincipal(this);
			controladorReservas.setVentanas(ventanas);
			controladorReservas.setEscenarioPrincipal(escenarioPrincipal);
			controladorReservas.iniciarReservas(reservas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void iniciarProfesores() {
		try {
			FXMLLoader cargador = new FXMLLoader(getClass().getResource("../vista/Profesores.fxml"));
			AnchorPane vistaProfesores = (AnchorPane) cargador.load();
			tabProfesores.setContent(vistaProfesores);

			ControladorProfesores controladorProfesores = cargador.getController();
			controladorProfesores.setControlador(controladorMVC);
			controladorProfesores.setVentanas(ventanas);
			controladorProfesores.setPrincipal(this);
			controladorProfesores.iniciar(profesores);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void iniciarAulas() {
		try {
			FXMLLoader cargador = new FXMLLoader(getClass().getResource("../vista/Aulas.fxml"));
			AnchorPane vistaAulas = (AnchorPane) cargador.load();
			tabAulas.setContent(vistaAulas);

			ControladorAulas controladorAulas = cargador.getController();
			controladorAulas.setControlador(controladorMVC);
			controladorAulas.setVentanas(ventanas);
			controladorAulas.setPrincipal(this);
			controladorAulas.iniciar(aulas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tabReservas = new Tab("Reservas");
		tabProfesores = new Tab("Profesores");
		tabAulas = new Tab("Aulas");
		verReservas();
		verProfesores();
		verAulas();
	}

	public void actualizarReservas() {
		reservas.setAll(controladorMVC.getReservas());
	}
	
	public void actualizarProfesores() {
		profesores.setAll(controladorMVC.getProfesores());
	}
	
	public void actualizarAulas() {
		aulas.setAll(controladorMVC.getAulas());
	}
}
