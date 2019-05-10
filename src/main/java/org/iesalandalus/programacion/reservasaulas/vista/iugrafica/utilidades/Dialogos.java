package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Dialogos {
	
	private static final String CSS = "../estilos/estilo_rfgr87_001.css";
	private static final String ID_BT_ACEPTAR = "btAceptar";
	private static final String ID_BT_CANCELAR = "btCancelar";
	private static Image logo = new Image(Dialogos.class.getResourceAsStream("../imagenes/logo.png"), 20, 20, true, true);

	private Dialogos() {
		//Evitamos que se puedan instanciar objetos
	}
	
	public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.ERROR);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoError(String titulo, String contenido) {
		Dialogos.mostrarDialogoError(titulo, contenido, null);
	}
	
	public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoInformacion(String titulo, String contenido) {
		Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
	}
	
	public static void mostrarDialogoAdvertencia(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.WARNING);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}
		dialogo.showAndWait();
		if (propietario != null)
			propietario.close();
	}
	
	public static void mostrarDialogoAdvertencia(String titulo, String contenido) {
		Dialogos.mostrarDialogoAdvertencia(titulo, contenido, null);
	}
	
	public static String mostrarDialogoTexto(String titulo, String contenido) {
		TextInputDialog dialogo = new TextInputDialog();
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);

		Optional<String> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() ? respuesta.get() : null);
	}
	
	public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
		Alert dialogo = new Alert(AlertType.CONFIRMATION);
		Stage stage = (Stage) dialogo.getDialogPane().getScene().getWindow();
		stage.getIcons().add(logo);
		dialogo.getDialogPane().getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.OK)).setId(ID_BT_ACEPTAR);
		((Button) dialogo.getDialogPane().lookupButton(ButtonType.CANCEL)).setId(ID_BT_CANCELAR);
		dialogo.setTitle(titulo);
		dialogo.setHeaderText(null);
		dialogo.setContentText(contenido);
		if (propietario != null) {
			dialogo.initModality(Modality.APPLICATION_MODAL);
			dialogo.initOwner(propietario);
		}

		Optional<ButtonType> respuesta = dialogo.showAndWait();
		return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
	}
	
	public static void acercaDe() {
		Alert dialogo = new Alert(AlertType.INFORMATION);
		dialogo.setTitle("Acerca de ...");
		DialogPane panelDialogo = dialogo.getDialogPane();
		panelDialogo.getStylesheets().add(Dialogos.class.getResource(CSS).toExternalForm());
		VBox contenido = new VBox();
		contenido.setAlignment(Pos.CENTER);
		contenido.setPadding(new Insets(20, 20, 0, 20));
		contenido.setSpacing(20);
		Image logo = new Image(Dialogos.class.getResourceAsStream("../imagenes/acercade.png"), 200, 200, true, true);
		Label texto = new Label("Reservas de aulas V4");
		contenido.getChildren().addAll(new ImageView(logo), texto);
		panelDialogo.setHeader(contenido);
		dialogo.showAndWait();
	}
}
