package org.iesalandalus.programacion.reservasaulas.modelo.mongodb.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.utilidades.MongoDB;

import com.mongodb.client.MongoCollection;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Aulas {

	private static final String COLECCION = "aulas";

	private MongoCollection<Document> coleccionAulas;

	// --- BUILDERS ---

	public Aulas() {
		coleccionAulas = MongoDB.getBD().getCollection(COLECCION);
	}

	// --- METHODS ---

	public List<Aula> getAulas() {
		List<Aula> aulas = new ArrayList();
		for (Document documentoAula : coleccionAulas.find()) {
			aulas.add(MongoDB.obtenerAulaDesdeDocumento(documentoAula));
		}
		return aulas;
	}

	public int getNumAulas() {
		return (int)coleccionAulas.countDocuments();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		}
		if (buscar(aula) != null) {
			throw new OperationNotSupportedException("El aula ya existe.");
		} else {
			coleccionAulas.insertOne(MongoDB.obtenerDocumentoDesdeAula(aula));
		}
	}

	public Aula buscar(Aula aula) {
		Document documentoAula = coleccionAulas.find().filter(eq(MongoDB.NOMBRE, aula.getNombre())).first();
		return MongoDB.obtenerAulaDesdeDocumento(documentoAula);
	}

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		if (buscar(aula) != null) {
			coleccionAulas.deleteOne(eq(MongoDB.NOMBRE, aula.getNombre()));
		} else {
			throw new OperationNotSupportedException("El aula a borrar no existe.");
		}
	}
	
	public boolean comprobarDependencias(List<Reserva> reservas, Aula aula) throws OperationNotSupportedException {
		boolean esDependiente = false;
		for(Reserva reserva : reservas) {
			if(reserva.getAula().equals(aula)) {
				esDependiente = true;
			}
		}
		if(esDependiente) {
			throw new OperationNotSupportedException("Esta Aula tiene reservas realizadas y no se puede eliminar.");
		}
		return esDependiente;
	}

	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		for (Aula aula : getAulas()) {
			representacion.add(aula.toString());
		}
		return representacion;
	}
}
