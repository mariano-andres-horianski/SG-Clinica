package clinica.habitaciones;
/**
 * Representa una habitación del tipo <b>terapia intensiva</b> dentro de la clínica.
 * <p>
 * Su costo diario es mayor que el de
 * otros tipos de habitación y, además, crece de forma <em>potencial</em> con respecto
 * a la cantidad de días de internación.
 * </p>
 *
 * <p>
 * El costo total se calcula según la siguiente fórmula:
 * </p>
 * <pre>
 * costoTotal = (costoDia) ^ dias
 * </pre>
 * donde:
 * <ul>
 *   <li><code>costoDia</code> = 5000 (valor base por día)</li>
 *   <li><code>dias</code> = cantidad de días de internación</li>
 * </ul>
 *
 *
 */
public class HabitacionTerapiaIntensiva implements IHabitacion 
{
	 /** Costo base por día de internación en terapia intensiva. */
	private static final double costoDia = 5000;
	/**
     * Calcula el costo total de internación en terapia intensiva.
     * <p>
     * El valor crece de forma potencial según la cantidad de días:
     * <code>costoTotal = costoDia ^ dias</code>.
     * </p>
     *
     * @param dias la cantidad de días de internación
     * @return el costo total calculado para el período especificado
     */
    @Override
    public double calcularCosto(long dias) 
    {
        return Math.pow(costoDia, dias);
    }
    
    @Override
    public String getTipo() 
    {
        return "Terapia intensiva";
    }
}
