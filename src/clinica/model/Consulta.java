package clinica.model;

import java.time.LocalDate;

/**
 * Representa una consulta médica realizada por un médico a un paciente.
 * <p>
 * Cada consulta contiene información sobre el médico que la realiza,
 * el paciente atendido, el importe de la consulta y la fecha de realización.
 * </p>
 * <p>
 * Las consultas se pueden ordenar cronológicamente utilizando compareTo(Consulta).
 * </p>
 */
public class Consulta implements Comparable<Consulta> {
	private IMedico medico;
	private Paciente paciente;
	private double importe;
	private LocalDate fecha;
	/**
     * Crea una consulta con fecha definida.
     * <p>
     * El importe se calcula como el honorario del médico más un 20%.
     * </p>
     *
     * @param medico el médico que realiza la consulta
     * @param paciente el paciente atendido
     * @param fecha la fecha de la consulta (puede ser null)
     */
	public Consulta(IMedico medico, Paciente paciente, LocalDate fecha) {
		super();
		this.medico = medico;
		this.paciente = paciente;
		this.importe = medico.getHonorario() * 1.2;
		this.fecha = fecha;
	}

	public Consulta(IMedico medico, Paciente paciente) {
		super();
		this.medico = medico;
		this.paciente = paciente;
		this.importe = medico.getHonorario() * 1.2;
		this.fecha = null; // La fecha se determina cuando se hace la facturacion
	}

	public IMedico getMedico() {
		return medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public double getImporte() {
		return importe;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	/**
     * Compara esta consulta con otra según la fecha de realización.
     *
     * Se utiliza para ordenar consultas cronológicamente:
     * <ul>
     *   <li>Consultas con fecha null se consideran mayores que cualquier fecha definida.</li>
     *   <li>Si ambas fechas son null, se consideran iguales.</li>
     * </ul>
     *
     *
     * @param otra La otra consulta a comparar
     * @return un valor negativo, cero o positivo según el orden cronológico
     */
	@Override
    public int compareTo(Consulta otra) {
        if (this.fecha == null && otra.fecha == null) 
        	return 0; 	  // ambas null => iguales
        else if (this.fecha == null) 
        	return 1;     // this > otra
        else if (otra.fecha == null) 
        	return -1;    // this < otra
        // las fechas null son mayores a cualquier fecha no null

        return this.fecha.compareTo(otra.fecha);
    }
	
}
