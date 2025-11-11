package clinica;

import java.time.LocalDate;

import clinica.exceptions.MedicoNotRegisteredException;
import clinica.exceptions.PacienteNotFoundException;
import clinica.habitaciones.IHabitacion;
import clinica.model.Factura;
import clinica.model.IMedico;
import clinica.model.Paciente;
import clinica.model.Reporte;
import excepciones.AsociadoDuplicadoException;
import negocio.*;

public class Main 
{
	public static void main(String[] args) 
	{
		try 
		{
			// ----------------- Inicializo clínica -----------------
			SingletonClinica clinica = SingletonClinica.getInstance();

			// ----------------- Creo médicos con Factory -----------------
			IMedico medico1 = clinica.crearMedico("111", "Carlitos", "MDQ", "2231111111", "Av JBJ", 742, 1001,
					"clinico", "residente", "doctorado");
			IMedico medico2 = clinica.crearMedico("222", "Cacho", "MDQ", "2232222222", "Av Libertad", 123, 1002,
					"cirujano", "permanente");

			clinica.registrarMedico(medico1);
			clinica.registrarMedico(medico2);

			// ----------------- Creo pacientes -----------------
			Paciente paciente1 = clinica.crearPaciente("333", "Juan Perez", "MDQ", "2233333333", "Calle Falsa", 123, 1,
					"Mayor");
			Paciente paciente2 = clinica.crearPaciente("444", "Pablo Perez", "MDQ", "2233333333", "Calle Falsa", 123, 1,
					"Mayor");

			clinica.registrarPaciente(paciente1);
			clinica.registrarPaciente(paciente2);

			// ----------------- Creo habitación -----------------
			IHabitacion habitacion1 = clinica.crearHabitacion("Privada");

			// ----------------- Ingreso pacientes -----------------
			clinica.ingresaPaciente(paciente1);
			clinica.ingresaPaciente(paciente2);

			// ----------------- Atiendo pacientes varias veces -----------------
			clinica.atiendePaciente(medico1, paciente1);
			clinica.atiendePaciente(medico2, paciente1);
			paciente1.setFechaIngreso(LocalDate.of(2025, 9, 03)); // Pruebo fecha de ingreso hace un mes
			clinica.internaPaciente(paciente1, habitacion1);
			
			clinica.atiendePaciente(medico1, paciente2);
			clinica.atiendePaciente(medico2, paciente2);
			
			// ----------------- Egreso pacientes y genero facturas -----------------
			Factura factura1 = clinica.egresaPaciente(paciente1);
			Factura factura2 = clinica.egresaPaciente(paciente2);

			// ----------------- Muestro factura -----------------
			System.out.println(factura1);
			System.out.println(factura2);
			
			// ----------------- Generar reportes -----------------
			LocalDate desde = LocalDate.of(2025, 9, 1);
			LocalDate hasta = LocalDate.of(2025, 10, 31);

			Reporte reporte1 = clinica.generarReporte(medico1, desde, hasta);
			System.out.println(reporte1);
			Reporte reporte2 = clinica.generarReporte(medico2, desde, hasta);
			System.out.println(reporte2);
			
			// PRUEBAS ASOCIADO + AMBULANCIA
			Asociado a1 = clinica.crearAsociado("777", "David", "Miramar", "223333333", "Av. Libertador", 25);
			Asociado a2 = clinica.crearAsociado("888", "Martin", "Miramar", "223333333", "Av. Libertador", 25);
			Asociado a3 = clinica.crearAsociado("999", "Gian", "Miramar", "223333333", "Av. Libertador", 25);
			Asociado a4 = clinica.crearAsociado("101", "Kevin", "Miramar", "223333333", "Av. Libertador", 25);
			
			try {
				clinica.registrarAsociado(a1);
				clinica.registrarAsociado(a2);
				clinica.registrarAsociado(a3);
				clinica.registrarAsociado(a4);
			} catch (AsociadoDuplicadoException e) {
				e.printStackTrace();
			}
			
			System.out.println(clinica.mostrarAsociados());
			
			Thread ta = new Thread(new RetornoAutomatico(clinica.getAmbulancia()));
			Thread t1 = new Thread(a1);
			Thread t2 = new Thread(a2);
			Thread t3 = new Thread(a3);
			Thread t4 = new Thread(a4);
			
			System.out.println("============= SIMULACION =============");
			
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			ta.start();
		} 
		catch (PacienteNotFoundException | MedicoNotRegisteredException e) 
		{
			e.printStackTrace();
		}
	}
}
