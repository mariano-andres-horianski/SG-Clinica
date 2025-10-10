package clinica.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Reporte {
	private IMedico medico;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private ArrayList<Consulta> consultasMedico;

	public Reporte(IMedico medico, LocalDate fechaInicio, LocalDate fechaFin, ArrayList<Consulta> consultasMedico) {
		this.medico = medico;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.consultasMedico = consultasMedico != null ? consultasMedico : new ArrayList<>();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (consultasMedico.isEmpty()) {
			sb.append("Médico: ").append(medico.getNya()).append("\n");
			sb.append("No existen consultas registradas para este médico.\n");
		} else {
			sb.append("REPORTE DE ACTIVIDAD MÉDICA\n");
			sb.append("Médico: ").append(medico.getNya()).append("\n");
			sb.append("Periodo: ").append(fechaInicio).append(" a ").append(fechaFin).append("\n\n");

			// Ordenar por fecha (usa compareTo de Consulta)
			Collections.sort(consultasMedico);

			LocalDate fechaActual = null;
			double total = 0;

			for (Consulta c : consultasMedico) {
				LocalDate fechaConsulta = c.getFecha();
				if (fechaConsulta != null) // ignorar sin fecha
					if (!(fechaConsulta.isBefore(fechaInicio) || fechaConsulta.isAfter(fechaFin))) { // fuera del rango
																										// pedido
						if (!fechaConsulta.equals(fechaActual)) { // Mostrar la fecha como encabezado cuando cambia
							fechaActual = fechaConsulta;
							sb.append("Fecha: ").append(fechaConsulta).append("\n");
						}
						sb.append(String.format("  Paciente: %-25s  Importe: $%.2f\n", c.getPaciente().getNya(),
								c.getImporte()));

						total += c.getImporte();
					}
			}
			sb.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");
		}
		return sb.toString();
	}
}
