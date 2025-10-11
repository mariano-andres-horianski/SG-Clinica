package clinica.exceptions;
/**
 * Excepción lanzada cuando el tipo de contratación de un médico no es reconocido
 * o no coincide con las opciones válidas del sistema.
 * <p>
 * Se utiliza durante la creación de médicos en clinica.factories.MedicoFactory.
 * </p>
 */
public class ContratacionNotFoundException extends Exception 
{
	/**
     * Crea una nueva excepción indicando que la contratación no fue encontrada.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public ContratacionNotFoundException(String mensaje)
	{
		super(mensaje);
	}
}
