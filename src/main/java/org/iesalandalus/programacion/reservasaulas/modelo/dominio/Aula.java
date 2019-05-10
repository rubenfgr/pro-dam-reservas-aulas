package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import java.io.Serializable;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Aula implements Serializable {

	private static final float PUNTOS_POR_PUESTO = 0.5f;
	private static final int MIN_PUESTOS = 10;
	private static final int MAX_PUESTOS = 100;
	private String nombre;
	private int puestos;

	// --- BUILDERS ---

	public Aula(String nombre, int puestos) {
		this.setNombre(nombre);
		this.setPuestos(puestos);
	}

	public Aula(Aula aula) {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede copiar un aula nula.");
		}
		this.setNombre(aula.getNombre());
		this.setPuestos(aula.getPuestos());
	}

	// --- METHODS ---

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre del aula no puede ser nulo.");
		}
		if (nombre.equals("")) {
			throw new IllegalArgumentException("El nombre del aula no puede estar vacío.");
		}
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getPuestos() {
		return this.puestos;
	}

	private void setPuestos(int puestos) {
		if (puestos < MIN_PUESTOS || puestos > MAX_PUESTOS) {
			throw new IllegalArgumentException("El número de puestos no es correcto.");
		}
		this.puestos = puestos;
	}

	public float getPuntos() {
		return this.puestos * PUNTOS_POR_PUESTO;
	}

	// --- OVERRIDDES ---

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Aula other = (Aula) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[nombre=" + nombre + ", puestos=" + puestos + "]";
	}
}
