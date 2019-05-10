package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import java.time.LocalDate;

public class PermanenciaPorTramo extends Permanencia {

	private static final int PUNTOS = 10;
	private Tramo tramo;

	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		this.setTramo(tramo);
	}

	public PermanenciaPorTramo(String dia, Tramo tramo) {
		super(dia);
		this.setTramo(tramo); 
	}

	public PermanenciaPorTramo(PermanenciaPorTramo permanenciaPorTramo) {
		super();
		if (permanenciaPorTramo == null) {
			throw new IllegalArgumentException("No se puede copiar una permanencia nula.");
		}
		this.setDia(permanenciaPorTramo.getDia());
		this.setTramo(permanenciaPorTramo.getTramo());
	}

	public Tramo getTramo() {
		return this.tramo;
	}

	private void setTramo(Tramo tramo) {
		if (tramo == null) {
			throw new IllegalArgumentException("El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo = tramo;
	}

	public int getPuntos() {
		return PUNTOS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tramo == null) ? 0 : tramo.hashCode());
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
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		if (dia == null) {
			if (other.dia != null) {
				return false;
			}
		} else if (!dia.equals(other.dia)) {
			return false;
		} else {
			if (tramo != other.tramo)
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[dia=" + dia.format(FORMATO_DIA) + ", tramo=" + tramo + "]";
	}

}
