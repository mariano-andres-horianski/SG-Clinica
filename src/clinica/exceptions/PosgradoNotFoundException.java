package clinica.exceptions;
/**
 * Excepción lanzada cuando se intenta acceder o procesar un paciente
 * que no está registrado o no se encuentra en las listas correspondientes.
 */
public class PosgradoNotFoundException extends Exception 
{
	/**
     * Crea una nueva excepción indicando que el paciente no fue encontrado.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public PosgradoNotFoundException(String mensaje) 
	{
		super(mensaje);
	}
}
