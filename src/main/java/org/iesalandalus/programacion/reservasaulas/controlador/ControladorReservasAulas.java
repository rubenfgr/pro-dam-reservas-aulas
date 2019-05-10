package org.iesalandalus.programacion.reservasaulas.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.IModeloReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;

public class ControladorReservasAulas implements IControladorReservasAulas {

	IModeloReservasAulas modeloReservasAulas;
	IVistaReservasAulas vistaReservasAulas;
	
	public ControladorReservasAulas(IModeloReservasAulas modeloReservasAulas, IVistaReservasAulas vistaReservasAulas) {
		this.modeloReservasAulas = modeloReservasAulas;
		this.vistaReservasAulas = vistaReservasAulas;
		this.vistaReservasAulas.setControlador(this);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#comenzar()
	 */
	@Override
	public void comenzar() {
		modeloReservasAulas.comenzar();
		this.vistaReservasAulas.comenzar(); 
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#salir()
	 */
	@Override
	public void salir() {
		modeloReservasAulas.terminar();
		System.out.println("Cerrando aplicación...");
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#insertarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public void insertarAula(Aula aula) throws OperationNotSupportedException{
		this.modeloReservasAulas.insertarAula(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#borrarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public void borrarAula(Aula aula) throws OperationNotSupportedException {
		this.modeloReservasAulas.borrarAula(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#buscarAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public Aula buscarAula(Aula aula) {
		return this.modeloReservasAulas.buscarAula(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#representarAulas()
	 */
	@Override
	public List<String> representarAulas() {
		return this.modeloReservasAulas.representarAulas();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#insertarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public void insertarProfesor(Profesor profesor) throws OperationNotSupportedException {
		this.modeloReservasAulas.insertarProfesor(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#borrarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public void borrarProfesor(Profesor profesor) throws OperationNotSupportedException {
		this.modeloReservasAulas.borrarProfesor(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#buscarProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		return this.modeloReservasAulas.buscarProfesor(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#representarProfesores()
	 */
	@Override
	public List<String> representarProfesores() {
		return this.modeloReservasAulas.representarProfesores();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#realizarReserva(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva)
	 */
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException {
		this.modeloReservasAulas.realizarReserva(reserva);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#anularReserva(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva)
	 */
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException {
		this.modeloReservasAulas.anularReserva(reserva);
	}
	
	@Override
	public Reserva buscarReserva(Reserva reserva) {
		return this.modeloReservasAulas.buscarReserva(reserva);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#representarReservas()
	 */
	@Override
	public List<String> representarReservas() {
		return this.modeloReservasAulas.representarReservas();
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#getReservasAula(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula)
	 */
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		return this.modeloReservasAulas.getReservasAula(aula);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#getReservasProfesor(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor)
	 */
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return this.modeloReservasAulas.getReservasProfesor(profesor);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#getReservasPermanencia(org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia)
	 */
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return this.modeloReservasAulas.getReservasPermanencia(permanencia);
	}
	
	/* (non-Javadoc)
	 * @see org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas#consultarDisponibilidad(org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula, org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia)
	 */
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return this.modeloReservasAulas.consultarDisponibilidad(aula, permanencia);
	}

	@Override
	public List<Reserva> getReservas() {
		return modeloReservasAulas.getReservas();
	}
	
	@Override
	public void guardar() {
		modeloReservasAulas.terminar();
	}

	@Override
	public List<Aula> getAulas() {
		return modeloReservasAulas.getAulas();
	}

	@Override
	public List<Profesor> getProfesores() {
		return modeloReservasAulas.getProfesores();
	}
}
