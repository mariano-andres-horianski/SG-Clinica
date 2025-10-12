package clinica.model;
/**
 * Representa la especialidad de <b>Pediatra</b> dentro de la clínica.
 * <p>
 * Extiende la clase Especialidad y define la forma específica
 * de calcular el honorario de un médico pediatra.
 * </p>
 * */
public class MPediatra extends Especialidad {
	/**
	 * Crea una instancia de médico Pediatra
	 */
	public MPediatra() {
		super("Pediatra");
	}
	/**
     * Calcula el honorario de un médico pediatra a partir de un valor base.
     * <p>
     * La fórmula utilizada es: <code>honorario = base * 1.07</code>
     * </p>
     *
     * @param base el valor base del honorario
     * @return el honorario calculado aplicando el factor de la especialidad
     */
	@Override
	public double getHonorario(double base) {
		return base * 1.07;
	}

}
