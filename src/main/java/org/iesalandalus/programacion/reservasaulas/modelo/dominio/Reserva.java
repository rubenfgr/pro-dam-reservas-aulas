package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Reserva implements Serializable {

	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;

	// --- BUILDERS ---

	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		this.setProfesor(profesor);
		this.setAula(aula);
		this.setPermanencia(permanencia);
	}

	public Reserva(Reserva reserva) {
		if (reserva == null) { 
			throw new IllegalArgumentException("No se puede copiar una reserva nula.");
		}
		this.setProfesor(reserva.getProfesor());
		this.setAula(reserva.getAula());
		this.setPermanencia(reserva.getPermanencia());
	}

	// --- METHODS ---

	private void setProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("La reserva debe estar a nombre de un profesor.");
		}
		this.profesor = new Profesor(profesor);
	}

	public Profesor getProfesor() {
		return new Profesor(this.profesor);
	}

	private void setAula(Aula aula) {
		if (aula == null) {
			throw new IllegalArgumentException("La reserva debe ser para un aula concreta.");
		}
		this.aula = new Aula(aula);
	}

	public Aula getAula() {
		return new Aula(this.aula);
	}

	private void setPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new IllegalArgumentException("La reserva se debe hacer para una permanencia concreta.");
		}
		if (permanencia instanceof PermanenciaPorHora) {
			this.permanencia = new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		} else {
			this.permanencia = new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
	}

	public Permanencia getPermanencia() {
		if (permanencia instanceof PermanenciaPorHora) {
			return new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		} else {
			return new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
	}

	public float getPuntos() {
		return this.aula.getPuntos() + this.permanencia.getPuntos();
	}

	// --- OVERRIDES ---

	@Override
	public String toString() {
		return "[profesor=" + profesor + ", aula=" + aula + ", permanencia=" + permanencia + ", puntos=" + getPuntos()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aula == null) ? 0 : aula.hashCode());
		result = prime * result + ((permanencia == null) ? 0 : permanencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (aula == null) {
			if (other.aula != null) {
				return false;
			}
		} else if (!aula.equals(other.aula)) {
			return false;
		}
		if (permanencia == null) {
			if (other.permanencia != null) {
				return false;
			}
		} else if (!permanencia.equals(other.permanencia)) {
			return false;
		}
		return true;
	}
}
