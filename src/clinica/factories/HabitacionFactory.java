package clinica.factories;

import clinica.habitaciones.*;
/**
 * Fábrica responsable de la creacion de instancias de Habitacion
 * <p>Las habitaciones pueden ser de tipo Compartida, Privada o de Terapia Intensiva</p>
 */
public class HabitacionFactory 
{
	/**
	 * Crea una habitación en base al tipo especificado
	 * @param tipo El tipo de habitación ("Compartida", "Privada" o "Terapia Insentiva")
	 * @return una instancia de IHabitacion del tipo correspondiente
	 */
    public IHabitacion crearHabitacion(String tipo) 
    {
        if (tipo.equalsIgnoreCase("Compartida")) 
        {
            return new HabitacionCompartida();
        }
        if (tipo.equalsIgnoreCase("Privada")) 
        {
            return new HabitacionPrivada();
        }
        if (tipo.equalsIgnoreCase("TerapiaIntensiva")) 
        {
            return new HabitacionTerapiaIntensiva();
        }
        return null;
    }
}
