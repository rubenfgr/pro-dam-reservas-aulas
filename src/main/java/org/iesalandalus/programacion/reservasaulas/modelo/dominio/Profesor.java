package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import java.io.Serializable;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Profesor implements Serializable {
	private static final String ER_TELEFONO = "950[0-9]{6}|[679][0-9]{8}";
	private static final String ER_CORREO = "\\w[\\.\\w+]*@\\w+\\.\\w+";
	private String nombre;
	private String correo;
	private String telefono;

	// --- BUILDERS ---

	public Profesor(String nombre, String correo) {
		this.setNombre(nombre);
		this.setCorreo(correo);
	}

	public Profesor(String nombre, String correo, String telefono) {
		this.setNombre(nombre);
		this.setCorreo(correo);
		this.setTelefono(telefono);
	}

	public Profesor(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede copiar un profesor nulo.");
		}
		this.setNombre(profesor.getNombre());
		this.setCorreo(profesor.getCorreo());
		this.setTelefono(profesor.getTelefono());
	}

	// --- METHODS ---

	private void setNombre(String nombre) {
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre del profesor no puede ser nulo.");
		}
		if (nombre.equals("")) {
			throw new IllegalArgumentException("El nombre del profesor no puede estar vacío.");
		}
		this.nombre = nombre;
	}

	public void setCorreo(String correo) {
		if (correo == null) {
			throw new IllegalArgumentException("El correo del profesor no puede ser nulo.");
		}
		if (correo.matches(ER_CORREO)) {
			this.correo = correo;
		} else {
			throw new IllegalArgumentException("El correo del profesor no es válido.");
		}
	}

	public void setTelefono(String telefono) {
		if (telefono != null) {
			if (telefono.matches(ER_TELEFONO)) {
				this.telefono = telefono;
			} else {
				throw new IllegalArgumentException("El teléfono del profesor no es válido.");
			}
		}
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getCorreo() {
		return this.correo;
	}

	public String getTelefono() {
		return this.telefono;
	}

	// --- OVERRIDES ---

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
		Profesor other = (Profesor) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (telefono == null) {
			return "[nombre=" + nombre + ", correo=" + correo + "]";
		}
		return "[nombre=" + nombre + ", correo=" + correo + ", telefono=" + telefono + "]";
	}
}
