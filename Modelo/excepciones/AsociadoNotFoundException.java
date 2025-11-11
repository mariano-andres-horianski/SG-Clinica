package excepciones;
/**
 * Excepci�n lanzada cuando se intenta eliminar un asociado que no está registrado
 */
public class AsociadoNotFoundException extends Exception 
{
	/**
     * Crea una nueva excepci�n indicando que el asociado no se encuentra registrado.
     *
     * @param mensaje mensaje descriptivo del error
     */
	public AsociadoNotFoundException(String mensaje) 
	{
		super(mensaje);
	}
}
