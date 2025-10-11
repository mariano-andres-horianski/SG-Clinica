package clinica.exceptions;
/**
 * Excepción lanzada cuando se intenta operar con un médico que no está
 * registrado en el sistema de la clínica.
 */
public class MedicoNotRegisteredException extends Exception 
{
	/**
     * Crea una nueva excepción indicando que el médico no se encuentra registrado.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public MedicoNotRegisteredException(String mensaje) 
	{
		super(mensaje);
	}
}
