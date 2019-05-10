package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

/**
 * @author RubenFrancisco
 * @version 0
 */
public enum Opcion {
	SALIR("Salir o volver atras") {
		public void ejecutar() {
			vista.salir();  
		}
	},
	INSERTAR_AULA("Insertar aula") {
		public void ejecutar() {
			vista.insertarAula();
		}
	},
	BORRAR_AULA("Borrar aula") {
		public void ejecutar() {
			vista.borrarAula();
		}
	},
	BUSCAR_AULA("Buscar aula") {
		public void ejecutar() {
			vista.buscarAula();
		}
	},
	LISTAR_AULAS("Listar aulas") {
		public void ejecutar() {
			vista.listarAulas();
		}
	},
	INSERTAR_PROFESOR("Insertar profesor") {
		public void ejecutar() {
			vista.insertarProfesor();
		}
	},
	BORRAR_PROFESOR("Borrar profesor") {
		public void ejecutar() {
			vista.borrarProfesor();
		}
	},
	BUSCAR_PROFESOR("Buscar profesor") {
		public void ejecutar() {
			vista.buscarProfesor();
		}
	},
	LISTAR_PROFESORES("Listar profesores") {
		public void ejecutar() { 
			vista.listarProfesores();
		}
	},
	INSERTAR_RESERVA("Realizar reserva") {
		public void ejecutar() {
			vista.realizarReserva();
		}
	},
	BORRAR_RESERVA("Anular reserva") {
		public void ejecutar() {
			vista.anularReserva();
		}
	},
	LISTAR_RESERVAS("Listar reservas") {
		public void ejecutar() {
			vista.listarReservas();
		}
	},
	LISTAR_RESERVAS_AULA("Listar reservas aula") {
		public void ejecutar() {
			vista.listarReservasAula();
		}
	},
	LISTAR_RESERVAS_PROFESOR("Listar reservas profesor") {
		public void ejecutar() {
			vista.listarReservasProfesor();
		}
	},
	LISTAR_RESERVAS_PERMANENCIA("Listar reservas permanencia") {
		public void ejecutar() {
			vista.listarReservasPermanencia();
		}
	},
	CONSULTAR_DISPONIBILIDAD("Consultar disponibilidad") {
		public void ejecutar() {
			vista.consultarDisponibilidad();
		}
	};
	
	private String mensajeAMostrar;
	private static VistaReservasAulas vista;
	
	private Opcion(String mensajeAMostrar) {
		this.mensajeAMostrar = mensajeAMostrar;
	}
	
	public String getMensaje() {
		return this.mensajeAMostrar;
	}
	
	public abstract void ejecutar();
	
	protected static void setVista(VistaReservasAulas vista) {
		Opcion.vista = vista;
	}
	
	@Override
	public String toString() {
		return ordinal() + ".- " + this.mensajeAMostrar;
	}
	
	public static Opcion getOpcionSegunOrdinal(int ordinal) {
		Opcion opcion = null;
		if(Opcion.esOrdinalValido(ordinal)) {
			opcion = Opcion.values()[ordinal];
		} else {
			throw new IllegalArgumentException("Ordinal de la opción no válido.");
		}
		return opcion;
	}
	
	public static boolean esOrdinalValido(int ordinal) {
		boolean valido = false;
		if(ordinal >= 0 && ordinal < Opcion.values().length) {
			valido = true;
		}
		return valido;
	}
}
