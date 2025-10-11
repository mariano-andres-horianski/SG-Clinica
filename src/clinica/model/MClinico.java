package clinica.model;
/**
 * Representa la especialidad de <b>Médico Clínico</b> dentro de la clínica.
 * <p>
 * Extiende la clase Especialidad y define la forma específica
 * de calcular el honorario de un médico clínico.
 * </p>
 *
 * @see Especialidad
 */
public class MClinico extends Especialidad {
	/**
	 * Crea una instancia de médico clínico
	 */
	public MClinico() {
		super("clinico");
	}
	/**
     * Calcula el honorario de un médico clínico a partir de un valor base.
     * <p>
     * La fórmula utilizada es: <code>honorario = base * 1.05</code>
     * </p>
     *
     * @param base el valor base del honorario
     * @return el honorario calculado aplicando el factor de la especialidad
     */
	@Override
	public double getHonorario(double base) {
		return base * 1.05;
	}

}
