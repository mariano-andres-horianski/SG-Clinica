package clinica.habitaciones;
/**
 * Representa una habitación privada dentro de la clínica.
 * <p>
 * Su costo depende de la cantidad de días de internación, aplicando distintos
 * multiplicadores según el rango de días.
 * </p>
 *
 *
 * Las reglas de cálculo del costo son las siguientes:
 * <ul>
 *   <li>1 día de internación: costo = 1 * costoDia</li>
 *   <li>2 a 5 días de internación: costo = cantidad de días * costoDia * 1.3</li>
 *   <li>6 o más días de internación: costo = cantidad de días * costoDia * 2</li>
 * </ul>
 *
 *
 */
public class HabitacionPrivada implements IHabitacion 
{

    /** Costo base por día de internación en habitación privada. */
    private static final double costoDia = 2000;
    /**
     * Calcula el costo total de internación en una habitación privada
     * aplicando multiplicadores según la cantidad de días.
     *
     * @param dias la cantidad de días de internación
     * @return el costo total calculado según las reglas de la habitación privada
     */
    @Override
    public double calcularCosto(long dias) 
    {
    	double costo=0;
        if (dias == 1) 
        {
            costo=costoDia;
        } 
        else 
        	if (dias <= 5) 
        	{
        		costo= dias * costoDia * 1.3;
        	} 
        	else 
        	{
        		costo=  dias * costoDia * 2;
        	}
        return costo;
    }

    @Override
    public String getTipo() 
    {
        return "Habitación privada";
    }
}
