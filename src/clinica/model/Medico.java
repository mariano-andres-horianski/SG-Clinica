package clinica.model;


public class Medico extends Persona implements IMedico {
	public static final double honorarioBase = 20000;
	private int nroMat;
	private Especialidad especialidad;

	public Medico(String dni, String nya, String ciudad, String telefono, Domicilio domicilio, int nroMat,
			Especialidad especialidad) {
		super(dni, nya, ciudad, telefono, domicilio);
		this.nroMat = nroMat;
		this.especialidad = especialidad;
	}

	public int getNroMat() {
		return nroMat;
	}

	public Especialidad getEspecialidad() {
		return this.especialidad;
	}

	public double getHonorario() {
		return this.especialidad.getHonorario(honorarioBase);
	}

	@Override
	public String toString() {
		return "Nombre Medico: " + this.getNya() + "        Especialidad: " + this.especialidad;
	}

}