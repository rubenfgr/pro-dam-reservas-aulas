package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.controlador.ControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iutextual.VistaReservasAulas;

/**
 * @author RubenFrancisco
 * @version 3
 */
public class MainApp_IUTextual_MongoDB {

	public static void main(String[] args) {
		IModeloReservasAulas modelo = new ModeloReservasAulas();
		IVistaReservasAulas vista = new VistaReservasAulas();
		IControladorReservasAulas controlador = new ControladorReservasAulas(modelo, vista);
		controlador.comenzar();
	}
}
