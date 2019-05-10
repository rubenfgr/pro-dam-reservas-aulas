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

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class Aulas {

	private static final String NOMBRE_FICHERO_AULAS = "archivos" + File.separator + "aulas.dat";

	private List<Aula> conjuntoAulas;

	// --- BUILDERS ---

	public Aulas() {
		this.conjuntoAulas = new ArrayList<>();
	}

	public Aulas(Aulas aulas) {
		this.setAulas(aulas);
	}

	// --- METHODS ---

	private void setAulas(Aulas aulas) {
		if (aulas == null) {
			throw new IllegalArgumentException("No se pueden copiar aulas nulas.");
		}
		this.conjuntoAulas = this.copiaProfundaAulas(aulas.conjuntoAulas);
	}

	private List<Aula> copiaProfundaAulas(List<Aula> conjuntoAulas) {
		List<Aula> conjuntoAulasSalida = new ArrayList<>();
		for (Aula aula : conjuntoAulas) {
			conjuntoAulasSalida.add(new Aula(aula));
		}
		return conjuntoAulasSalida;
	}

	public List<Aula> getAulas() {
		return this.copiaProfundaAulas(this.conjuntoAulas);
	}

	public int getNumAulas() {
		return this.conjuntoAulas.size();
	}

	public void insertar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede insertar un aula nula.");
		}
		if (this.conjuntoAulas.contains(aula)) {
			throw new OperationNotSupportedException("El aula ya existe.");
		} else {
			this.conjuntoAulas.add(new Aula(aula));
		}
	}

	public Aula buscar(Aula aula) {
		Aula aulaSalida = null;
		if (this.conjuntoAulas.contains(aula)) {
			aulaSalida = new Aula(aula);
		}
		return aulaSalida;
	}

	public void borrar(Aula aula) throws OperationNotSupportedException {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede borrar un aula nula.");
		}
		if (this.conjuntoAulas.contains(aula)) {
			this.conjuntoAulas.remove(aula);
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
		List<String> cadenaSalida = new ArrayList<>();
		for (Aula aula : this.conjuntoAulas) {
			cadenaSalida.add(aula.toString());
		}
		return cadenaSalida;
	}

	public void leer() {
		File archivo = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
			while (true) {
				insertar((Aula) entrada.readObject());
			}
		} catch (ClassNotFoundException e1) {
			System.out.println("No se encuentra la clase a leer.");
		} catch (FileNotFoundException e2) {
			System.out.println("No se puede abrir el fichero de Aulas.");
		} catch (EOFException e3) {
			System.out.println("Se cargaron las Aulas del archivo \"aulas.dat\" correctamente.");
		} catch (IOException e4) {
			System.out.println("Se produjo un error leyendo el archivo \"aulas.dat\"");
		} catch (OperationNotSupportedException e5) {
			System.out.println(e5.getMessage());
		}
	}

	public void escribir() {
		File archivo = new File(NOMBRE_FICHERO_AULAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
			for (Aula aula : conjuntoAulas) {
				salida.writeObject(aula);
			}
			System.out.println("Guardando la lista de Aulas en el archivo aulas.dat");
		} catch (FileNotFoundException e1) {
			System.out.println("Imposible guardar el fichero de Reservas.");
		} catch (IOException e) {
			System.out.println("Se produjo un error escribiendo el archivo \"aulas.dat\"");
		}
	}
}
