package org.iesalandalus.programacion.reservasaulas.modelo.mongodb;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.dao.Aulas;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.dao.Profesores;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.dao.Reservas;
import org.iesalandalus.programacion.reservasaulas.modelo.mongodb.utilidades.MongoDB;

/**
 * @author RubenFrancisco
 * @version 0
 */
public class ModeloReservasAulas implements IModeloReservasAulas {

	private Profesores profesores;
	private Aulas aulas;
	private Reservas reservas;
	
	// --- BUILDER ---
	
	public ModeloReservasAulas() {
		this.profesores = new Profesores(); 
		this.aulas = new Aulas();
		this.reservas = new Reservas(); 
	}
	
	@Override
	public void comenzar() {
		MongoDB.establecerConexion();
	}
	
	@Override
	public void terminar() {
		MongoDB.cerrarCliente();
	}
	
	// --- METHODS ---
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getAulas()
	 */
	@Override
	public List<Aula> getAulas() {
		return aulas.getAulas();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getNumAulas()
	 */
	@Override
	public int getNumAulas() {
		return aulas.getNumAulas();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#representarAulas()
	 */
	@Override
	public List<String> representarAulas() {
		return aulas.representar();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#buscarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#insertarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#borrarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getProfesores()
	 */
	@Override
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getNumProfesores()
	 */
	@Override
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#representarProfesores()
	 */
	@Override
	public List<String> representarProfesores() {
		return profesores.representar();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#buscarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#insertarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#borrarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getReservas()
	 */
	@Override
	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getNumReservas()
	 */
	@Override
	public int getNumReservas() {
		return reservas.getNumReservas();
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#representarReservas()
	 */
	@Override
	public List<String> representarReservas() {
		return reservas.representar();
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#buscarReserva(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva)
	 */
	@Override
	public Reserva buscarReserva(Reserva reserva) {
		return reservas.buscar(reserva);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#realizarReserva(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva)
	 */
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.insertar(reserva);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#anularReserva(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva)
	 */
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		reservas.borrar(reserva);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getReservasAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getReservasProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#getReservasPermanencia(org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia)
	 */
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}

	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas#consultarDisponibilidad(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula, org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia)
	 */
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
	
}
