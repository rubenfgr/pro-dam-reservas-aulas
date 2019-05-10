package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import java.time.format.DateTimeParseException;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class VistaReservasAulas implements IVistaReservasAulas {

	private static final String NOMBRE_VALIDO = "Nombre";
	private static final String CORREO_VALIDO = "a@a.a";
	private static final String ERROR = "ERROR. ";

	private IControladorReservasAulas controlador;

	public VistaReservasAulas() {
		Opcion.setVista(this);
	}

	@Override
	public void setControlador(IControladorReservasAulas controlador) {
		this.controlador = controlador;
	}

	@Override
	public void comenzar() {
		Consola.mostrarCabecera("Gestión de reservas de Aulas - IES Al-Ándalus");
		int ordinal;
		do {
			Consola.mostrarMenu();
			ordinal = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinal);
			opcion.ejecutar();
		} while (ordinal != Opcion.SALIR.ordinal());
	}

	public void salir() {
		controlador.salir();
	}

	public void insertarAula() {
		Consola.mostrarCabecera("Insertar Aula");
		try {
			Aula aula = Consola.leerAula();
			controlador.insertarAula(aula);
			System.out.println("Se agregó el Aula correctamente. " + aula);
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void borrarAula() {
		Consola.mostrarCabecera("Borrar Aula");
		try {
			Aula aula = controlador.buscarAula(new Aula(Consola.leerNombreAula(), 10));
			controlador.borrarAula(aula);
			System.out.println("Se borro el Aula correctamente. " + aula);
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void buscarAula() {
		Consola.mostrarCabecera("Buscar Aula");
		try {
			Aula aulaBuscar = new Aula(Consola.leerNombreAula(), 10);
			Aula aulaRecibida = controlador.buscarAula(aulaBuscar);
			if (aulaRecibida != null) {
				System.out.println("Se encontró el Aula. " + aulaRecibida);
			} else {
				System.out.println("No se encontro el Aula. [nombre=" + aulaBuscar.getNombre() + "]");
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void listarAulas() {
		Consola.mostrarCabecera("Listado Aulas");
		List<String> aulas = controlador.representarAulas();
		if (aulas.isEmpty()) {
			System.out.println("No existen Aulas para representar.");
		} else {
			int i = 1;
			for (String s : aulas) {
				System.out.println(i++ + ".- " + s);
			}
		}
	}

	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar Profesor");
		try {
			Profesor profesor = Consola.leerProfesor();
			controlador.insertarProfesor(profesor);
			System.out.println("Se inserto el Profesor correctamente. " + profesor);
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar Profesor");
		try {
			String nombre = Consola.leerNombreProfesor();
			Profesor profesor = new Profesor(nombre, CORREO_VALIDO);
			controlador.borrarProfesor(profesor);
			System.out.println("Se borro correctamente el profesor . " + "[nombre=" + nombre + "]");
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar Profesor");
		try {
			String nombre = Consola.leerNombreProfesor();
			Profesor profesorBuscado = new Profesor(nombre, CORREO_VALIDO);
			Profesor profesorDevuelto = controlador.buscarProfesor(profesorBuscado);
			if (profesorDevuelto == null) {
				System.out.println("No se encontró el profesor. " + "[nombre=" + nombre + "]");
			} else {
				System.out.println("Se encontró el profesor. " + profesorDevuelto);
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void listarProfesores() {
		Consola.mostrarCabecera("Lista de Profesores");
		List<String> profesores = controlador.representarProfesores();
		if (profesores.isEmpty()) {
			System.out.println("No existen profesores para representar.");
		} else {
			int i = 1;
			for (String s : profesores) {
				System.out.println(i++ + ".- " + s);
			}
		}
	}

	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar Reserva");
		try {
			String nombre = Consola.leerNombreProfesor();
			Profesor profesor = controlador.buscarProfesor(new Profesor(nombre, CORREO_VALIDO));
			if (profesor == null) {
				System.out.println("El profesor debe estar registrado. " + "[nombre=" + nombre + "]");
			} else {
				Reserva reserva = leerReserva(profesor);
				if (reserva != null) {
					controlador.realizarReserva(reserva);
					System.out.println("Se realizo la reserva correctamente. " + reserva);
				}
			}
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	private Reserva leerReserva(Profesor profesor) {
		Reserva reserva = null;
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), 10);
			Aula aulaSalida = controlador.buscarAula(aula);
			if (aulaSalida == null) {
				System.out.println("El Aula debe estar registrada. " + aula);
			} else {
				Permanencia permanencia = Consola.leerPermanencia();
				reserva = new Reserva(profesor, aula, permanencia);
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
		return reserva;
	}

	public void anularReserva() {
		Consola.mostrarCabecera("Anular Reserva");
		try {
			Reserva reserva = leerReserva(new Profesor(NOMBRE_VALIDO, CORREO_VALIDO));
			Reserva reservaEncontrada = controlador.buscarReserva(reserva);
			if (reservaEncontrada == null) {
				System.out.println("La reserva no existe. " + reserva);
			} else {
				controlador.anularReserva(reservaEncontrada);
				System.out.println("La reserva se anulo correctamente. " + reservaEncontrada);
			}
		} catch (OperationNotSupportedException e) {
			System.out.println(ERROR + e.getMessage());
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void listarReservas() {
		Consola.mostrarCabecera("Lista de reservas");
		List<String> reservas = controlador.representarReservas();
		if (reservas.isEmpty()) {
			System.out.println("No se ha realizado ninguna reserva.");
		} else {
			int i = 1;
			for (String s : reservas) {
				System.out.println(i++ + ".- " + s);
			}
		}
	}

	public void listarReservasAula() {
		Consola.mostrarCabecera("Lista de reservas por Aula");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(),10);
			List<Reserva> reservas = controlador.getReservasAula(aula);
			if (reservas.isEmpty()) {
				System.out.println("No existen reservas para el Aula: " + aula);
			} else {
				Consola.mostrarCabecera("Lista de reservas por Aula: " + aula);
				int i = 1;
				for (Reserva reserva : reservas) {
					System.out.println(i++ + ".- " + reserva);
				}
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Lista de reservas por Profesor");
		try {
			Profesor profesor = new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
			List<Reserva> reservas = controlador.getReservasProfesor(profesor);
			if (reservas.isEmpty()) {
				System.out.println("No existen reservas para el Profesor: [nombre=" + profesor.getNombre() + "]");
			} else {
				Consola.mostrarCabecera("Lista de reservas por Profesor: [nombre=" + profesor.getNombre() + "]");
				int i = 1;
				for (Reserva reserva : reservas) {
					System.out.println(i++ + ".- " + reserva);
				}
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void listarReservasPermanencia() {
		Consola.mostrarCabecera("Lista de reservas por Permanencia");
		try {
			Permanencia permanencia = Consola.leerPermanencia();
			List<Reserva> reservas = controlador.getReservasPermanencia(permanencia);
			if (reservas.isEmpty()) {
				System.out.println("No existen reservas para la Permanencia: " + permanencia);
			} else {
				Consola.mostrarCabecera("Lista de reservas por Permanencia: " + permanencia);
				int i = 1;
				for (Reserva reserva : reservas) {
					System.out.println(i++ + ".- " + reserva);
				}
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		}
	}

	public void consultarDisponibilidad() {
		Consola.mostrarCabecera("Consultar disponibilidad");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(),10);
			if (controlador.buscarAula(aula) != null) {
				Permanencia permanencia = Consola.leerPermanencia();
				boolean disponible = controlador.consultarDisponibilidad(aula, permanencia);
				if (disponible) {
					System.out.println("Esta disponible. " + aula + " - " + permanencia);
				} else {
					System.out.println("NO esta disponible. " + aula + " - " + permanencia);
				}
			} else {
				System.out.println("No hay reservas para esa Aula." + aula);
			}
		} catch (IllegalArgumentException e1) {
			System.out.println(ERROR + e1.getMessage());
		} catch (DateTimeParseException e2) {
			System.out.println(ERROR + "Fecha incorrecta.");
		}
	}
}
