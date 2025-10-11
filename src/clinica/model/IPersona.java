package clinica.model;
/**
 * Interfaz que define los métodos básicos para cualquier persona en la clínica.
 * <p>
 * Incluye información de identificación, contacto y domicilio. Las clases
 * que implementen esta interfaz, como Persona, Paciente o
 * IMedico, deben proporcionar estas propiedades.
 * </p>
 */
public interface IPersona {
	
	public String getDni();

	public String getNya();

	public String getCiudad();

	public String getTelefono();

	public Domicilio getDomicilio();

}
