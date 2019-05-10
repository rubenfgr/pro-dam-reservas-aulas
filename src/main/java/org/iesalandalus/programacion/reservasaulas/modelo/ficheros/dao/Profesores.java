package org.iesalandalus.programacion.reservasaulas.modelo.ficheros.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Profesores {

	private static final String NOMBRE_FICHERO_PROFESORES = "archivos" + File.separator + "profesores.dat";
	private List<Profesor> grupoProfesores;

	// --- BUILDERS ---

	public Profesores() {
		this.grupoProfesores = new ArrayList<>();
	}

	public Profesores(Profesores profesores) {
		this.setProfesores(profesores);
	}

	// --- METHODS ---

	private void setProfesores(Profesores profesores) {
		if (profesores == null) {
			throw new IllegalArgumentException("No se pueden copiar profesores nulos.");
		}
		this.grupoProfesores = this.copiaProfundaProfesores(profesores.grupoProfesores);
	}

	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> profesoresSalida = new ArrayList<>();
		for (Profesor profesor:profesores) {
			profesoresSalida.add(new Profesor(profesor));
		}
		return profesoresSalida;
	}

	public List<Profesor> getProfesores() {
		return this.copiaProfundaProfesores(this.grupoProfesores);
	}

	public int getNumProfesores() {
		return this.grupoProfesores.size();
	}

	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede insertar un profesor nulo.");
		}
		if (this.grupoProfesores.contains(profesor)) {
			throw new OperationNotSupportedException("El profesor ya existe.");
		} else {
			this.grupoProfesores.add(new Profesor(profesor));
		}
	}

	public Profesor buscar(Profesor profesor) {
		Profesor profesorSalida = null;
		if(this.grupoProfesores.contains(profesor)) {
			profesorSalida = new Profesor(profesor);
		}
		return profesorSalida;
	}

	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) {
			throw new IllegalArgumentException("No se puede borrar un profesor nulo.");
		}
		if (this.grupoProfesores.contains(profesor)) {
			this.grupoProfesores.remove(profesor);
		} else {
			throw new OperationNotSupportedException("El profesor a borrar no existe.");
		}
	}
	
	public boolean comprobarDependencias(List<Reserva> reservas, Profesor profesor) throws OperationNotSupportedException {
		boolean esDependiente = false;
		for(Reserva reserva : reservas) {
			if(reserva.getProfesor().equals(profesor)) {
				esDependiente = true;
			}
		}
		if(esDependiente) {
			throw new OperationNotSupportedException("Este profesor tiene reservas realizadas y no se puede eliminar.");
		}
		return esDependiente;
	}

	public List<String> representar() {
		List<String> cadenaSalida = new ArrayList<>();
		for(Profesor profesor:this.grupoProfesores) {
			cadenaSalida.add(profesor.toString());
		}
		return cadenaSalida;
	}
	
	public void leer() {
		File archivo = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
			while (true) {
				insertar((Profesor) entrada.readObject());
			}
		} catch (ClassNotFoundException e1) {
			System.out.println("No se encuentra la clase a leer.");
		} catch (FileNotFoundException e2) {
			System.out.println("No se puede abrir el fichero de Profesores.");
		} catch (EOFException e3) {
			System.out.println("Se cargaron los profesores del archivo \"profesores.dat\" correctamente.");
		} catch (IOException e4) {
			System.out.println("Se produjo un error leyendo el archivo \"profesores.dat\"");
		} catch (OperationNotSupportedException e5) {
			System.out.println(e5.getMessage());
		}
	}

	public void escribir() {
		File archivo = new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
			for (Profesor profesor : grupoProfesores) {
				salida.writeObject(profesor);
			}
			System.out.println("Guardando la lista de Profesores en el archivo profesores.dat");
		} catch (FileNotFoundException e1) {
			System.out.println("Imposible guardar el fichero de Profesores.");
		} catch (IOException e2) {
			System.out.println("Se produjo un error escribiendo el archivo \"profesores.dat\"");
		}
	}
}
