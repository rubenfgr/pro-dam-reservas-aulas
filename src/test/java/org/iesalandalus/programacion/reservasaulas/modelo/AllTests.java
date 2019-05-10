package org.iesalandalus.programacion.reservasaulas.modelo;

import org.iesalandalus.programacion.reservasaulas.modelo.dao.AulasTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dao.ProfesoresTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dao.ReservasTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.AulaTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.ProfesorTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.ReservaTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHoraTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramoTest;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.TramoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PermanenciaPorTramoTest.class, PermanenciaPorHoraTest.class, TramoTest.class, 
	ProfesorTest.class, ReservaTest.class, AulaTest.class,
	AulasTest.class, ProfesoresTest.class, ReservasTest.class })
public class AllTests {

}
