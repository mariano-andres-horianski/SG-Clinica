package clinica.model.decorators;

import clinica.model.Domicilio;
import clinica.model.Especialidad;
import clinica.model.IMedico;
/**
 * Decorator que permite obtener los datos del médico
 */
public abstract class DecoratorMedico implements IMedico 
{
	protected IMedico encapsulado;
	/**
	 * Guarda un médico con sus datos y le delega la implementación de los métodos
	 * @param encapsulado El médico del que se quieren obtener los datos
	 */
	public DecoratorMedico(IMedico encapsulado) 
	{
		this.encapsulado = encapsulado;
	}

	@Override
	public String getDni() 
	{
		return this.encapsulado.getDni();
	}

	@Override
	public String getNya() 
	{
		return this.encapsulado.getNya();
	}

	@Override
	public String getCiudad() 
	{
		return this.encapsulado.getCiudad();
	}

	@Override
	public String getTelefono() 
	{
		return this.encapsulado.getTelefono();
	}

	@Override
	public Domicilio getDomicilio() 
	{
		return this.encapsulado.getDomicilio();
	}

	public int getNroMat() 
	{
		return this.encapsulado.getNroMat();
	}

	public Especialidad getEspecialidad() 
	{
		return this.encapsulado.getEspecialidad();
	}
	
	@Override
	public String toString() 
	{
		return "" + encapsulado;
	}

}
