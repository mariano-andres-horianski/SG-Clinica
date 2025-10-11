package clinica.model;
/**
 * Representa la especialidad de un médico
 */
public abstract class Especialidad {
	private String nombre; // de la especialidad
	/**
	 * Crea una especialidad.
	 * @param nombre Nombre de la espeicalidad
	 */
	public Especialidad(String nombre) {
		this.nombre = nombre;
	}

	public abstract double getHonorario(double base);

	public String getNombre() {
		return nombre;
	}

	@Override
	public String toString() {
		return "" + nombre;
	}

	
}
