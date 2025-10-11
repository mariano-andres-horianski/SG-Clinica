package clinica.factories;
import clinica.model.*;
import clinica.d.dispatch.*;
/**
 * Fábrica responsable de la creacion de instancias de Paciente
 * según el rango etario
 */
public class PacienteFactory 
{
	/**
	 * 
	 * @param dni Documento Nacional de Identidad
	 * @param nya Nombre y Apellido
	 * @param ciudad Ciudad de residencia de la Persona
	 * @param telefono Teléfono de contacto de la Persona
	 * @param calle Calle del domicilio de la Persona
	 * @param altura Altura del domicilio de la Persona
	 * @param nroHC Número de historia clínica del Paciente
	 * @param rangoEtario Rango etario del Paciente ("Niño", "Joven" o "Mayor)
	 * @return la instancia del Paciente creado
	 */
    public Paciente crearPaciente(String dni, String nya, String ciudad, String telefono, String calle, int altura, int nroHC, String rangoEtario) 
    {
    	
    	Domicilio domicilio = new Domicilio(calle, altura);
    	
        if (rangoEtario.equalsIgnoreCase("Nino")) 
        {
            return new Nino(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
        if (rangoEtario.equalsIgnoreCase("Joven")) 
        {
            return new Joven(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
        if (rangoEtario.equalsIgnoreCase("Mayor")) 
        {
            return new Mayor(dni, nya, ciudad, telefono, domicilio, nroHC, rangoEtario);
        }
           return null;

    }
}
