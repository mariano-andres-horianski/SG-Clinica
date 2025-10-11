package clinica.model.decorators;

import clinica.model.IMedico;

/**
 * Decorator que le da un doctorado a un médico
 * <p>Añade 10% al honorario del médico.</p>
 */
public class DecoratorPosgradoDoctorado extends DecoratorPosgrado 
{
	/**
	 * Le añade un posgrado de tipo doctorado a un médico
	 * @param encapsulado El médico al que añadirle doctorado
	 */
	public DecoratorPosgradoDoctorado(IMedico encapsulado) 
	{
		super(encapsulado);
	}
	
	public double getHonorario() 
	{
		return this.encapsulado.getHonorario() * 1.1;
	}
	
}
