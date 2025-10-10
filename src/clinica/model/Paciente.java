package clinica.model;

import java.time.LocalDate;
import clinica.d.dispatch.*;


public abstract class Paciente extends Persona implements IPrioridad{
	private int nroHC;
	private String rangoEtario;
	private LocalDate fechaIngreso;

	public Paciente(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int nroHC,
			String rangoEtario) {
		super(dni, nya, ciudad, telefono, domicilio);
		this.nroHC = nroHC;
		this.rangoEtario = rangoEtario;
	}

	public int getNroHC() {
		return nroHC;
	}

	public String getRangoEtario() {
		return rangoEtario;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

}
