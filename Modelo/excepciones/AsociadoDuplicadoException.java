package excepciones;
/**
 * Excepci�n lanzada cuando se intenta registrar un asociado duplicado
 */
public class AsociadoDuplicadoException extends Exception 
{
	/**
     * Crea una nueva excepci�n indicando que el asociado se encuentra ya registrado.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public AsociadoDuplicadoException(String mensaje) 
	{
		super(mensaje);
	}
}
