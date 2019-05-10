package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.controlador.ControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.ficheros.ModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.VistaReservasAulas;

/**
 * @author RubenFrancisco
 * @version 4
 */
public class MainApp_IUGrafica_Ficheros {

	public static void main(String[] args) {
		IModeloReservasAulas modelo = new ModeloReservasAulas();
		IVistaReservasAulas vista = new VistaReservasAulas();
		IControladorReservasAulas controlador = new ControladorReservasAulas(modelo, vista);
		controlador.comenzar();
	}
}
