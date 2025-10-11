package clinica.habitaciones;
/**
 * Representa una habitación compartida dentro de la clínica.
 * <p>
 * Este tipo de habitación permite que varios pacientes compartan el mismo espacio,
 * y su costo diario es menor que el de habitaciones privadas o de terapia intensiva.
 * </p>
 *
 * <p>
 * El costo total se calcula de manera proporcional a la cantidad de días de internación:
 * </p>
 * <pre>
 * costoTotal = dias * costoDia
 * </pre>
 * donde:
 * <ul>
 *   <li><code>costoDia</code> = 1000 (valor base por día)</li>
 *   <li><code>dias</code> = cantidad de días de internación</li>
 * </ul>
 */
public class HabitacionCompartida implements IHabitacion 
{
    /** Costo base por día de internación en habitación compartida. */
    private static final double costoDia = 1000; 
    /**
     * Calcula el costo total de internación en una habitación compartida.
     *
     * @param dias la cantidad de días de internación
     * @return el costo total proporcional a la cantidad de días
     */
    @Override
    public double calcularCosto(long dias) 
    {
        return dias * costoDia;
    }

    @Override
    public String getTipo() 
    {
        return "Habitación compartida";
    }
}
