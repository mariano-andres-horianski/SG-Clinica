package clinica.model;

public abstract class Persona implements IPersona {
	private String dni, nya, ciudad, telefono; // nya = Nombre y Apellido
	private Domicilio domicilio;

	public Persona(String dni, String nya, String ciudad, String telefono, Domicilio domicilio) {
		this.dni = dni;
		this.nya = nya;
		this.ciudad = ciudad;
		this.telefono = telefono;
		this.domicilio = domicilio;
	}

	@Override
	public String getDni() {
		return dni;
	}

	@Override
	public String getNya() {
		return nya;
	}

	@Override
	public String getCiudad() {
		return ciudad;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	@Override
	public Domicilio getDomicilio() {
		return domicilio;
	}

}
