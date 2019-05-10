package org.iesalandalus.programacion.reservasaulas.modelo.ficheros.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Reservas {

	private static final String NOMBRE_FICHERO_RESERVAS = "archivos" + File.separator + "reservas.dat";
	private static final float MAX_PUNTOS_PROFESOR = 200;
	private List<Reserva> reservasRealizadas;

	// --- BUILDERS ---

	public Reservas() {
		this.reservasRealizadas = new ArrayList<>();
	}

	public Reservas(Reservas reservas) {
		this.setReservas(reservas);
	}

	// --- METHODS ---

	private void setReservas(Reservas reservas) {
		if (reservas == null) {
			throw new IllegalArgumentException("No se pueden copiar reservas nulas.");
		}
		this.reservasRealizadas = this.copiaProfundaReservas(reservas.reservasRealizadas);
	}

	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> reservasSalida = new ArrayList<>();
		for (Reserva reserva : reservas) {
			reservasSalida.add(new Reserva(reserva));
		}
		return reservasSalida;
	}

	public List<Reserva> getReservas() {
		return this.copiaProfundaReservas(this.reservasRealizadas);
	}

	public int getNumReservas() {
		return this.reservasRealizadas.size();
	}

	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede realizar una reserva nula.");
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException(
					"Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR) {
			throw new OperationNotSupportedException(
					"Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (this.reservasRealizadas.contains(reserva)) {
			throw new OperationNotSupportedException("La reserva ya existe.");
		}
		Reserva reservaActual = getReservaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaActual != null) {
			if (reservaActual.getPermanencia() instanceof PermanenciaPorTramo
					&& reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException(
						"Ya se ha realizado una reserva por tramo para este día y aula.");
			}
			if (reservaActual.getPermanencia() instanceof PermanenciaPorHora
					&& reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException(
						"Ya se ha realizado una reserva por hora para este día y aula.");
			}
		}
		reservasRealizadas.add(new Reserva(reserva));
	}

	// Nuevos métodos v2
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		boolean valido = false;
		int anoReserva = reserva.getPermanencia().getDia().getYear();
		int anoActual = LocalDate.now().getYear();
		int mesReserva = reserva.getPermanencia().getDia().getMonthValue();
		int mesActual = LocalDate.now().getMonthValue();
		if (anoReserva > anoActual) {
			valido = true;
		} else {
			if (mesActual < 12 && mesReserva > mesActual) {
				valido = true;
			}
		}
		return valido;
	}

	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntosGastados = 0;
		List<Reserva> lista = this.getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia());
		for (Reserva res : lista) {
			puntosGastados = puntosGastados + res.getPuntos();
		}
		return puntosGastados + reserva.getPuntos();
	}

	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate dia) {
		List<Reserva> reservasProfesor = new ArrayList<>();
		for (Reserva reserva : reservasRealizadas) {
			if (reserva.getProfesor().equals(profesor)
					&& reserva.getPermanencia().getDia().getMonthValue() == dia.getMonthValue()
					&& reserva.getPermanencia().getDia().getYear() == dia.getYear()) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}

	private Reserva getReservaDia(Aula aula, LocalDate dia) {
		if (dia == null) {
			throw new NullPointerException("No se puede buscar una reserva para un día nulo.");
		}
		if (aula == null) {
			throw new NullPointerException("No se puede buscar una reserva para un Aula nula.");
		}
		Reserva reservaSalida = null;
		for (Reserva reserva : reservasRealizadas) {
			if (reserva.getPermanencia().getDia().equals(dia)) {
				reservaSalida = new Reserva(reserva);
			}
		}
		return reservaSalida;
	}
	// Fin Nuevos métodos v2

	public Reserva buscar(Reserva reserva) {
		Reserva reservaSalida = null;
		if (this.reservasRealizadas.contains(reserva)) {
			reservaSalida = new Reserva(reserva);
		}
		return reservaSalida;
	}

	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede anular una reserva nula.");
		}
		if (this.reservasRealizadas.contains(reserva)) {
			this.reservasRealizadas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("La reserva a anular no existe.");
		}
	}

	public List<String> representar() {
		List<String> cadenaSalida = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			cadenaSalida.add(reserva.toString());
		}
		return cadenaSalida;
	}

	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> reservasProfesor = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getProfesor().equals(profesor)) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}

	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> reservasAulas = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getAula().equals(aula)) {
				reservasAulas.add(new Reserva(reserva));
			}
		}
		return reservasAulas;
	}

	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List<Reserva> reservasPermanencia = new ArrayList<>();
		for (Reserva reserva : this.reservasRealizadas) {
			if (reserva.getPermanencia().equals(permanencia)) {
				reservasPermanencia.add(new Reserva(reserva));
			}
		}
		return reservasPermanencia;
	}

	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de un aula nula.");
		}
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de una permanencia nula.");
		}
		Reserva reserva = new Reserva(new Profesor("a", "a@a.a"), aula, permanencia);
		if (this.reservasRealizadas.contains(reserva)) {
			return false;
		}
		return true;
	}
	
	public void leer() {
		File archivo = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
			while (true) {
				insertar((Reserva) entrada.readObject());
			}
		} catch (ClassNotFoundException e1) {
			System.out.println("No se encuentra la clase a leer.");
		} catch (FileNotFoundException e2) {
			System.out.println("No se puede abrir el fichero de Reservas.");
		} catch (EOFException e3) {
			System.out.println("Se cargaron las reservas del archivo \"reservas.dat\" correctamente.");
		} catch (IOException e4) {
			System.out.println("Se produjo un error leyendo el archivo \"reservas.dat\"");
		} catch (OperationNotSupportedException e5) {
			System.out.println(e5.getMessage());
		}
	}

	public void escribir() {
		File archivo = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
			for (Reserva reserva : reservasRealizadas) {
				salida.writeObject(reserva);
			}
			System.out.println("Guardando la lista de Reservas en el archivo reservas.dat");
		} catch (FileNotFoundException e1) {
			System.out.println("Imposible guardar el fichero de Reservas.");
		} catch (IOException e2) {
			System.out.println("Se produjo un error escribiendo el archivo \"reservas.dat\"");
		}
	}
}
