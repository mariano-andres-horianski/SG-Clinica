package persistencia.Excepciones;

/**
 * Excepci�n que indica que no se encontr� un asociado con el identificador especificado.
 */
public class AsociadoNotFoundException extends Exception{
    /**
     * Crea una nueva excepcion con el mensaje especificado.
     * 
     * @param mensaje descripcion del error.
     */
	public AsociadoNotFoundException(String mensaje) { 
		super(mensaje); 
	}
}
