package clinica.exceptions;
/**
 * Excepción lanzada cuando la especialidad médica indicada no se encuentra
 * entre las especialidades válidas del sistema.
 * <p>
 * Se utiliza durante la creación de médicos en clinica.factories.MedicoFactory.
 * </p>
 */
public class EspecialidadNotFoundException extends Exception 
{
	/**
     * Crea una nueva excepción indicando que la especialidad no fue encontrada.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public EspecialidadNotFoundException(String mensaje) 
	{
		super(mensaje);
	}
}
