package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Consola {

	private Consola() {
	}

	public static void mostrarMenu() {
		mostrarCabecera("Reservas de aulas");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String cabecera) {
		System.out.printf("%n%s%n", cabecera);
		String cadena = "%0" + cabecera.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "*"));
	}

	public static int elegirOpcion() { 
		int opcion;
		do {
			System.out.print("Elige una opción: ");
			opcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(opcion));
		return opcion;
	}

	public static Aula leerAula() {
		String nombre = leerNombreAula();
		int puestos;
		System.out.print("Introduce el número de puestos del Aula: ");
		puestos = Entrada.entero();
		return new Aula(nombre, puestos);
	}

	public static String leerNombreAula() {
		System.out.print("Introduce el nombre del Aula: ");
		return Entrada.cadena();
	}

	public static Profesor leerProfesor() {
		String nombre = leerNombreProfesor();
		System.out.print("Introduce el correo del Profesor: ");
		String correo = Entrada.cadena();
		return new Profesor(nombre, correo);
	}

	public static String leerNombreProfesor() {
		System.out.print("Introduce el nombre del Profesor: ");
		return Entrada.cadena();
	}

	public static Tramo leerTramo() {
		int indice = 0;
		System.out.println("1.- " + Tramo.MANANA);
		System.out.println("2.- " + Tramo.TARDE);
		do {
			System.out.print("Eliga un tramo (índice): ");
			indice = Entrada.entero();
		} while (indice < 1 && indice > 2);
		return Tramo.values()[indice - 1];
	}

	public static String leerDia() {
		System.out.print("Introduce la fecha (dd/mm/aaaa):");
		return Entrada.cadena();
	}
	
	public static String leerHora() {
		System.out.print("Introduce la hora (hh:mm):");
		return Entrada.cadena();
	}
	
	public static Permanencia leerPermanencia() {
		Permanencia permanencia = null;
		int elegida = elegirPermanencia();
		if(elegida == 1) {
			permanencia = new PermanenciaPorTramo(leerDia(), leerTramo());
		}
		if (elegida == 2) {
			permanencia = new PermanenciaPorHora(leerDia(), leerHora());
		}
		return permanencia;
	}
	
	public static int elegirPermanencia() {
		int indice = 0;
		System.out.println("1.- Permanencia por Tramo");
		System.out.println("2.- Permanencia por Hora");
		do {
			System.out.print("Eliga un tipo de permanencia (índice): ");
			indice = Entrada.entero();
		} while (indice < 1 && indice > 2);
		return indice;
	}
}
