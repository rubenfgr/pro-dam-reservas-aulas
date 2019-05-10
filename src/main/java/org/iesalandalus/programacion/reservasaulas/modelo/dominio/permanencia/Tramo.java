package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

/**
 * @author RubenFrancisco
 * @version 0
 */
public enum Tramo {
	MANANA("Mañana"),
	TARDE("Tarde");
	
	private String cadenaAMostrar;
	
	private Tramo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	@Override
	public String toString() {
		return this.cadenaAMostrar;
	}
}
