package temp;

import clinica.exceptions.*;
import clinica.model.*;
import clinica.habitaciones.*;
import java.time.LocalDate;

import clinica.*;

public class Prueba {

	public static void main(String[] args) {

		try {
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
			
		} catch (PacienteNotFoundException | MedicoNotRegisteredException e) {
			e.printStackTrace();
		}
	}
}
