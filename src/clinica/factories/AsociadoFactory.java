package clinica.factories;
import clinica.model.*;
import negocio.Asociado;
/**
 * F�brica responsable de la creacion de instancias de Asociado
 *
 */
public class AsociadoFactory 
{
	/**
	 * 
	 * @param dni Documento Nacional de Identidad
	 * @param nya Nombre y Apellido
	 * @param ciudad Ciudad de residencia de la Persona
	 * @param telefono Tel�fono de contacto de la Persona
	 * @param calle Calle del domicilio de la Persona
	 * @param altura Altura del domicilio de la Persona
	 * @param solicitudes Cantidad de solicitudes para ambulancia
	 * @return la instancia del Paciente creado
	 */
    public Asociado crearAsociado(String dni, String nya, String ciudad, String telefono, String calle, int altura) 
    {
    	
    	Domicilio domicilio = new Domicilio(calle, altura);
    	
        return new Asociado(dni, nya, ciudad, telefono, domicilio);
    }
}
